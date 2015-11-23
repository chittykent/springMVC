/*
 * Copyright 2002-2101 SIM group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.app.simple.web.commons;




import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



/**
 * @author zheng.guo
 *	JUnit测试公共入口
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/application-context.xml"
		,"file:src/main/webapp/WEB-INF/springMVC-servlet.xml"})
public class ParentApplicationTest {
	private static String SUCCESS="0";
	
	/***
	 * 测试响应
	 * @param map
	 */
	protected void testResponse(Map<String,Object> map){
		JSONObject json=JSONObject.fromObject(map);
		System.out.println(json.toString());
		String status=map.get("status").toString();
		if(!status.equals(SUCCESS)){
			String msg=map.get("errMsg").toString();
			System.out.println("错误信息:"+msg);
		}
		assertEquals(SUCCESS, status);
	}
	
	@Test
	@Ignore
	public void test(){
		
	}
	/*protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemcachedClient memcachedClient;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	@Ignore("忽略此test")
	public void applicationContextTest() throws TimeoutException,
			InterruptedException, MemcachedException {
		Map<InetSocketAddress, String> map = memcachedClient.getVersions();

		assertThat(map.size(), Is.is(1));
	}

	@Test
	@Ignore("忽略对controle 测试！")
	public void restTampateTest() {
		String str = restTemplate.getForObject("http://www.baidu.com",
				String.class);
		logger.info(str);

		assertThat(str, StringContains.containsString("百度一下"));
	}*/
}
