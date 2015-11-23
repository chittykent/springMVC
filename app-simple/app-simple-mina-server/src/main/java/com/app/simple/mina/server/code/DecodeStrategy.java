package com.app.simple.mina.server.code;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.app.simple.mina.server.utils.ByteTools;
import com.app.simple.model.CardModel;
import com.app.simple.utils.DateUtil;
import com.app.simple.utils.FileUtil;

public class DecodeStrategy {
	private final Logger log = Logger.getLogger(this.getClass());
	private static List<String> headList = null;// 正确的报文头部
	private static String alarmHeadStr = "FA";// 报警协议的头
	private static String heartCenterStr = "FC";// 心跳协议中心节点的头
	private static String heartSingleCenterStr = "FD";// 心跳协议单中心节点的头
	private static String byteEnd = "0D0A";// 正确的报文尾部
	private static final int PROTOCOL_DATA_LENGTH = 18;//协议完整数据包长度
	/**
	 * 16进制报文：
	 * FA 13 3B BF 29 36 58 BE FB FB FB FB FB FB FB FB 0D 0A
	 * FA 13 38 0C 25 36 58 04 FB FB FB FB FB FB FB FB 0D 0A
	 * @Title: doDecode 所有报文长度都为18位
	 * @Description: 定位16进制数据包解析
	 * @param session
	 * @param in
	 * @param out
	 * @param @throws Exception
	 * @return
	 */
	public boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		in.mark();// 标记当前位置，以便reset
		int startPostion = in.markValue();// 获取标记位置
		int size = in.remaining();
		// 大小等于18才处理，否则等下面的
		if (size >= PROTOCOL_DATA_LENGTH) {
			byte[] byteArray = new byte[PROTOCOL_DATA_LENGTH];
			// 读取
			in.get(byteArray);
			// 获取包头
			String byteHeadStr = ByteTools.bytes2HexString(new byte[] { byteArray[0] }).toUpperCase();
			// 包尾
			String byteEndStr = ByteTools.bytes2HexString(new byte[] { byteArray[16], byteArray[17] }).toUpperCase();
			// 验证头尾
			if (validateHeadEnd(byteHeadStr, byteEndStr)) {
				// 验证总和
				if (validateDataTotal(byteHeadStr, byteArray)) {
					// 正确根据不同的报文调用原来的方法
					System.out.println("正确："
							+ ByteTools.bytes2HexString(byteArray)
									.toUpperCase());
					CardModel protocolEntity = transformPackage(byteArray);//报文转实体对象
					out.write(protocolEntity);//进入业务处理
					// 总和不对直接放日志丢弃
				} else {
					// 错误的数据包写文件
					writeErrorToFile(ByteTools.bytes2HexString(byteArray)
							.toUpperCase());
				}
				// 头尾不对,往后查找正确的头
			} else {
				errorHandle(byteArray, in, startPostion);
			}
			// 检查缓冲区内是否还有数据未读取，如果有就继续调用doDecode()方法，
			// 没有就停止对doDecode()方法的调用，直到有新的数据被缓冲
			return true;
		} else {
			// client会发数据包的头2个字节过来,造成后面数据总是不对,所以将不正确的报文全部销毁掉
			System.out.println("字节不够:" + size);
			// in.get(new byte[size]);
			// 框架不要再回调doDecode方法，直到网络获取到数据压进缓冲区时再调用
			in.reset();// reset到mark标记的位置
			return false;
		}
	}

	/***
	 * 1.验证接收数据头尾正确性
	 * 
	 * @param byteHead
	 *            头 FA(定位、告警) FC/FD(心跳)
	 * @param byteEnd
	 *            尾 0D0A为正确
	 * @return
	 */
	private boolean validateHeadEnd(String bHead, String bEnd) {
		boolean result = false;
		// 1.验证头尾是否正确
		if (getHeadList().contains(bHead) && byteEnd.equals(bEnd)) {
			result = true;
		}
		return result;
	}

	/***
	 * 获取头正确的数据集合
	 * 
	 * @return
	 */
	private List<String> getHeadList() {
		if (headList == null) {
			headList = new ArrayList<String>();
			System.out.println("加载head头数据..");
			headList.add(alarmHeadStr);// 报警和定位头
			headList.add(heartCenterStr);// 心跳
			headList.add(heartSingleCenterStr);// 心跳
		}
		return headList;
	}

	/***
	 * 2.验证和是否正确 在验证头尾后调用
	 * 
	 * @param byteHead
	 *            头 只有FA FC FD
	 * @param byteArray
	 *            接收的18字节数据
	 * @return
	 */
	private boolean validateDataTotal(String byteHead, byte[] byteArray) {
		boolean result = false;
		String msg = "";// 提示消息
		byte byteLength = 0;
		byte byteCount = 0;// 校验和、前面的数据总和
		// 报警
		if (byteHead.equals(alarmHeadStr)) {
			// 报警第八个为前面7个字节和
			for (int j = 0; j <= 6; j++) {
				byteLength += byteArray[j];
			}
			byteCount = byteArray[7];
			msg = "报警协议";
			// 心跳
		} else if (byteHead.equals(heartCenterStr)
				|| byteHead.equals(heartSingleCenterStr)) {
			// 校验和第15个字节存校验和
			for (int j = 0; j <= 13; j++) {
				byteLength += byteArray[j];
			}
			// 取15个校验和
			byteCount = byteArray[14];
			msg = "心跳协议";
		} else {
			System.out.println("未知错误，先调用1的话不应该到此处.byteHead:" + byteHead);
		}
		if (byteLength == byteCount) {
			result = true;
		} else {
			System.out.println(msg + "校验和不正确.");
			System.out.println("byteLength:" + byteLength + ".byteCount:"
					+ byteCount);
		}
		return result;
	}
	/***
	 * 报文错误处理 循环查找报文头，找到后定位到该位置 处理的报文字节放错误文件
	 * 
	 * @param byteArray
	 * @return
	 */
	private void errorHandle(byte[] byteArray, IoBuffer in, int startPostion) {
		// 错误buffer
		StringBuffer errorStr = new StringBuffer();
		String byteHeadStr = "";
		for (int i = 0; i < 18; i++) {
			// 检查是否是包头
			byteHeadStr = ByteTools
					.bytes2HexString(new byte[] { byteArray[i] }).toUpperCase();
			// 第一个跳过检查,直接放错误的字符串里面
			if (i == 0) {
				errorStr.append(byteHeadStr);
			} else {
				// 是包头
				if (headList.contains(byteHeadStr)) {
					// 定位到该位置，退出
					in.position(startPostion + i);
					break;
					// 不是包头放错误
				} else {
					errorStr.append(byteHeadStr);
				}

			}
		}
		System.out.println("errorStr:" + errorStr.toString());
		// 错误日志记录错误的16进制字符串
		writeErrorToFile(errorStr.toString());
	}
	/***
	 * 写错误的报文到一个文件中，分天新建日志文件
	 * 
	 * @param content
	 *            文件内容
	 * @return
	 */
	private boolean writeErrorToFile(String content) {
		log.error("丢弃的错误字节：" + content);
		String path = this.getClass().getResource("/").getPath();
		System.out.println("path:" + path);
		// 获取当前日期的年月日，格式yyyy-MM-dd
		String date = DateUtil.getConvertDateFormat(new Date());
		String fileName = path + "dataError" + date + ".txt";
		StringBuffer contentSB = new StringBuffer();
		contentSB.append(DateUtil.getDateMilliFormat(new Date()));
		contentSB.append("-------");
		contentSB.append(content);
		contentSB.append("\r\n");
		// 换行写
		return FileUtil.writeStringToFile(fileName, contentSB.toString(),
				"UTF-8", true);
	}

	/**报文转实体对象*/
	private CardModel transformPackage(byte[] dataPackage) {
		CardModel cardModel = new CardModel();
		String head = ByteTools.bytes2HexString(new byte[] { dataPackage[0] }).toUpperCase();//包头:报警或定位{FA},心跳{定位参考点类型:[FC(中心节点),FD(单中心节点)]}
		
		if(head.equals("FA")){//FA--报警或定位数据包
			String headSign = "FA";//头标识
			String cardNo   = ByteTools.bytes2HexString(new byte[] { dataPackage[1], dataPackage[2], dataPackage[3] }).toUpperCase();//定位卡号
			String distance = ByteTools.bytes2HexString(new byte[] { dataPackage[4] }).toUpperCase();// 距离
			String refPoint = ByteTools.bytes2HexString(new byte[] { dataPackage[5], dataPackage[6] }).toUpperCase();//定位参考点
			
			if (ByteTools.isNumeric(distance)) {
				cardModel.setRelativeDistance(distance);
			} else if (distance.equals("FF") || distance.equals("FE")) {
				cardModel.setRelativeDistance(distance);
				refPoint = refPoint.substring(2, 4)+refPoint.substring(0, 2);//告警参考点id均倒序
			} else {
				cardModel.setRelativeDistance(Integer.parseInt(distance, 16) + "");
			}
			cardModel.setHeadSign(headSign);
			cardModel.setCardNo(cardNo);
			cardModel.setReferencePoint(refPoint);
		}else if(head.equals("FC") || head.equals("FD")){//FC,FD--心跳数据包
			String centerId = ByteTools.bytes2HexString(new byte[] { dataPackage[1], dataPackage[2] }).toUpperCase();// 中心节点或单中心节点ID
			
			// 添加区域号集合
			List<String> areaNoList = new ArrayList<String>();
			areaNoList.add(centerId);
			
			int networkRefPointCount = dataPackage[3];//后续字节数 = 组网定位参考点个数*2
			if (networkRefPointCount % 2 == 0) {//组网定位参考点字节数正确 = 组网定位参考点个数正确
				// FC中心节点，只有中心节点才会有位置参考点ID
				if (head.equals("FC") && networkRefPointCount != 0) {
					// 获取位置参考点ID并放置区域号集合
					for (int i = 1; i <= networkRefPointCount;i+=2) {
						// 位置参考点ID
						String placeId = ByteTools.bytes2HexString(new byte[] {dataPackage[3 + i], dataPackage[3 + i + 1] }).toUpperCase();
						areaNoList.add(placeId);
					}
				}
			} else {
				log.error("有效参考字节数错误." + networkRefPointCount);
			}
			
			cardModel.setHeadSign(head);//定位参考点类型
			cardModel.setAreaNoList(areaNoList);
		}
		return cardModel;
	}
}
