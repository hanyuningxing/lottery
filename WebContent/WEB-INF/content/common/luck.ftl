 <!--  选择您的生肖-->
        <div class="right">
		         <div class="right_left" id="Bluck_tb_0">
		            <ul class="jxqmenu">
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}1" class="now" onclick="Bluck_HoverLi(${luckLotteryNum},1);return false;">我的生肖</a></li>
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}2" onclick="Bluck_HoverLi(${luckLotteryNum},2);return false;">我的星座</a></li>
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}3" onclick="Bluck_HoverLi(${luckLotteryNum},3);return false;">我的生日</a></li>
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}4" onclick="Bluck_HoverLi(${luckLotteryNum},4);return false;">我的QQ</a></li>
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}5" onclick="Bluck_HoverLi(${luckLotteryNum},5);return false;">我的姓名</a></li>
		              <li><a href="#" id="Bluck_tb_${luckLotteryNum}6" onclick="Bluck_HoverLi(${luckLotteryNum},6);return false;">我的手机</a></li>
		            </ul>
		          </div>
		      <div class="right_rig">
		        <div class="kong10"></div>
			        <!--生肖 -->
			        <div id="Bluck_tbc_${luckLotteryNum}1">
			            <div>
			            <p>选择您的生肖</p>
			            <p><select id="sx_v_${luckLotteryNum}" name="">
			              <option value="1">鼠</option>
			              <option value="2">牛</option>
			
			              <option value="3">虎</option>
			              <option value="4">兔</option>
			              <option value="5">龙</option>
			              <option value="6">蛇</option>
			              <option value="7">马</option>
			              <option value="8">羊</option>
			
			              <option value="9">猴</option>
			              <option value="10">鸡</option>
			              <option value="11">狗</option>
			              <option value="12">猪</option>
			            </select></p>
						<p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'sx');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
			            </div>
			        </div>
		        <!--星座 -->
		        <div id="Bluck_tbc_${luckLotteryNum}2" style="display:none">
		            <div>
		            <p>选择您的星座</p>
		            <p><select id="xz_v_${luckLotteryNum}" name="">
		              <option value="1">白羊座</option>
		              <option value="2">金牛座</option>
		              <option value="3">双子座</option>
		              <option value="4">巨蟹座</option>
		              <option value="5">狮子座</option>
		              <option value="6">处女座</option>
		              <option value="7">天秤座</option>
		              <option value="8">天蝎座</option>
		              <option value="9">射手座</option>
		              <option value="10">摩羯座</option>
		              <option value="11">水瓶座</option>
		              <option value="12">双鱼座</option>
		            </select></p>
					<p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'xz');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
		            </div>
		        </div>
		        <!--生日 -->
		        <div id="Bluck_tbc_${luckLotteryNum}3" style="display:none">
		            <div id="sr_v_1">
		                                选择您的生日
		            <p>
		               <select name="sr_v_${luckLotteryNum}Year">
						<option label="2012" value="2012">2012</option>
						<option label="2011" value="2011" selected="selected">2011</option>
						<option label="2010" value="2010">2010</option>
						<option label="2009" value="2009">2009</option>
						<option label="2008" value="2008">2008</option>
						<option label="2007" value="2007">2007</option>
						<option label="2006" value="2006">2006</option>
						<option label="2005" value="2005">2005</option>
						<option label="2004" value="2004">2004</option>
						<option label="2003" value="2003">2003</option>
						<option label="2002" value="2002">2002</option>
						
						<option label="2001" value="2001">2001</option>
						<option label="2000" value="2000">2000</option>
						<option label="1999" value="1999">1999</option>
						<option label="1998" value="1998">1998</option>
						<option label="1997" value="1997">1997</option>
						<option label="1996" value="1996">1996</option>
						<option label="1995" value="1995">1995</option>
						<option label="1994" value="1994">1994</option>
						<option label="1993" value="1993">1993</option>
						
						<option label="1992" value="1992">1992</option>
						<option label="1991" value="1991">1991</option>
						<option label="1990" value="1990">1990</option>
						<option label="1989" value="1989">1989</option>
						<option label="1988" value="1988">1988</option>
						<option label="1987" value="1987">1987</option>
						<option label="1986" value="1986">1986</option>
						<option label="1985" value="1985">1985</option>
						<option label="1984" value="1984">1984</option>
						
						<option label="1983" value="1983">1983</option>
						<option label="1982" value="1982">1982</option>
						<option label="1981" value="1981">1981</option>
						<option label="1980" value="1980">1980</option>
						<option label="1979" value="1979">1979</option>
						<option label="1978" value="1978">1978</option>
						<option label="1977" value="1977">1977</option>
						<option label="1976" value="1976">1976</option>
						<option label="1975" value="1975">1975</option>
						
						<option label="1974" value="1974">1974</option>
						<option label="1973" value="1973">1973</option>
						<option label="1972" value="1972">1972</option>
						<option label="1971" value="1971">1971</option>
						<option label="1970" value="1970">1970</option>
						<option label="1969" value="1969">1969</option>
						<option label="1968" value="1968">1968</option>
						<option label="1967" value="1967">1967</option>
						<option label="1966" value="1966">1966</option>
						
						<option label="1965" value="1965">1965</option>
						<option label="1964" value="1964">1964</option>
						<option label="1963" value="1963">1963</option>
						<option label="1962" value="1962">1962</option>
						<option label="1961" value="1961">1961</option>
						<option label="1960" value="1960">1960</option>
						<option label="1959" value="1959">1959</option>
						<option label="1958" value="1958">1958</option>
						<option label="1957" value="1957">1957</option>
						
						<option label="1956" value="1956">1956</option>
						<option label="1955" value="1955">1955</option>
						<option label="1954" value="1954">1954</option>
						<option label="1953" value="1953">1953</option>
						<option label="1952" value="1952">1952</option>
						<option label="1951" value="1951">1951</option>
						</select>
						<select name="sr_v_${luckLotteryNum}Month">
						<option label="01" value="01" selected="selected">01</option>
						<option label="02" value="02">02</option>
						
						<option label="03" value="03">03</option>
						<option label="04" value="04">04</option>
						<option label="05" value="05">05</option>
						<option label="06" value="06">06</option>
						<option label="07" value="07">07</option>
						<option label="08" value="08">08</option>
						<option label="09" value="09">09</option>
						<option label="10" value="10">10</option>
						<option label="11" value="11">11</option>
						
						<option label="12" value="12">12</option>
						</select>
						<select name="sr_v_${luckLotteryNum}Day">
						<option label="01" value="1">01</option>
						<option label="02" value="2">02</option>
						<option label="03" value="3">03</option>
						<option label="04" value="4" selected="selected">04</option>
						<option label="05" value="5">05</option>
						<option label="06" value="6">06</option>
						<option label="07" value="7">07</option>
						
						<option label="08" value="8">08</option>
						<option label="09" value="9">09</option>
						<option label="10" value="10">10</option>
						<option label="11" value="11">11</option>
						<option label="12" value="12">12</option>
						<option label="13" value="13">13</option>
						<option label="14" value="14">14</option>
						<option label="15" value="15">15</option>
						<option label="16" value="16">16</option>
						
						<option label="17" value="17">17</option>
						<option label="18" value="18">18</option>
						<option label="19" value="19">19</option>
						<option label="20" value="20">20</option>
						<option label="21" value="21">21</option>
						<option label="22" value="22">22</option>
						<option label="23" value="23">23</option>
						<option label="24" value="24">24</option>
						<option label="25" value="25">25</option>
						
						<option label="26" value="26">26</option>
						<option label="27" value="27">27</option>
						<option label="28" value="28">28</option>
						<option label="29" value="29">29</option>
						<option label="30" value="30">30</option>
						<option label="31" value="31">31</option>
						</select>
					</p>
		            <p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'sr');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
		            </div>
		
			        </div>
			        <!-- QQ-->
			        <div id="Bluck_tbc_${luckLotteryNum}4" style="display:none">
			            <div>
			            <p>输入您的QQ号码</p>
			            <p><input size="14" id="qq_v_${luckLotteryNum}" name="" type="text" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" /></p>
			            <p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'qq');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
			            </div>
			
			        </div>
			        <!--姓名 -->
			        <div id="Bluck_tbc_${luckLotteryNum}5" style="display:none">
			            <div>
			            <p>输入您的姓名</p>
			            <p><input size="14" id="xm_v_${luckLotteryNum}" name="" type="text" /></p>
			            <p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'xm');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
			            </div>
			
			        </div>
			        <!--手机 -->
			        <div id="Bluck_tbc_${luckLotteryNum}6" style="display:none">
			            <div>
			            <p>输入您的手机号码</p>
			            <p><input size="14" id="sj_v_${luckLotteryNum}" name="" type="text"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/></p>
			            <p><a href="#" onclick="choose_auto_select(${luckLotteryNum},'sj');return false;"><img src="<@c.url value= "/pages/images/xybt_tj.gif"/>"/></a></p>
			            </div>
			        </div>
		          <div class="kong10"></div>
		       </div>
        </div>