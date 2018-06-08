package serviceUtils;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.fan.manage.pojo.Menu;

import common.utils.Const;

/**
 * 权限处理
 * @author hanxiaofan
 *
 */
@SuppressWarnings("unchecked")
public class Jurisdiction {
	
	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
	 * @param menuUrl 菜单路径
	 * @return
	 */
	public static boolean hasJurisdiction(String menuUrl){
		String USERNAME = getUsername();	//获取当前登陆着的名字
		Session session = getSession();		//获取seesion会话
		//获取存在session中的菜单列表
		List<Menu> menuList = (List<Menu>) session.getAttribute(USERNAME+Const.SESSION_allmenuList);
		return readMenu(menuList,menuUrl,session,USERNAME);
	} 
	
	/**
	 * 校验菜单权限，并初始化按钮权限，用于页面按钮显示与否
	 * @param menuList：传入的总菜单
	 * @param menuUrl:访问地址
	 * @param session
	 * @param USERNAME
	 * @return
	 */
	public static boolean readMenu(List<Menu> menuList,String menuUrl,Session session,String USERNAME){
		for (int i = 0; i < menuList.size(); i++) {
			//访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
			if(menuList.get(i).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
				if(!menuList.get(i).isHasMenu()){
					return false;
				}else{
					//按钮权限(增删改查)
					Map<String,String> map = (Map<String, String>) session.getAttribute(USERNAME+Const.SESSION_QX);
					map.remove("add");
					map.remove("del");
					map.remove("edit");
					map.remove("cha");
					//获取菜单已经匹配好的菜单
					String MENU_ID = menuList.get(i).getMENU_ID();
					Boolean isAdmin = "admin".equals(USERNAME);
					map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
					map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
					map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
					map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
					session.removeAttribute(USERNAME + Const.SESSION_QX);
					session.setAttribute(USERNAME + Const.SESSION_QX, map);	//重新分配按钮权限
					return true;
					
				}
			}else{
				if(!readMenu(menuList.get(i).getSubMenu(),menuUrl,session,USERNAME)){//继续排查其子菜单
					return false;
				}
			}
			
		}
		return true;
	}
	
	/**
	 * 按钮权限(方法中校验)
	 * @param menuUrl	菜单路径
	 * @param type		类型(add,del,edit,cha)
	 * @return
	 */
	public static boolean buttonJurisdiction(String menuUrl,String type){
		
		String USERNAME =getUsername();
		Session session = getSession();
		//获取菜单列表
		List<Menu> menuList = (List<Menu>) session.getAttribute(USERNAME+Const.SESSION_allmenuList);
		return readMenuButton(menuList,menuUrl,session,USERNAME,type);
	}
	
	/**
	 * 校验按钮权限(递归处理)
	 * @param menuList	传入的总菜单(设置菜单时)
	 * @param menuUrl	访问地址
	 * @param session
	 * @param USERNAME
	 * @param type
	 * @return
	 */
	public static boolean readMenuButton(List<Menu> menuList,String menuUrl,Session session,String USERNAME, String type){
		for (int i = 0; i < menuList.size(); i++) {
			if(menuList.get(i).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){	//访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
				if(!menuList.get(i).isHasMenu()){
					return false;
				}else{
					Map<String, String> map = (Map<String, String>)session.getAttribute(USERNAME + Const.SESSION_QX);//按钮权限(增删改查)
					String MENU_ID =  menuList.get(i).getMENU_ID();
					Boolean isAdmin = "admin".equals(USERNAME);
					if("add".equals(type)){
						return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
					}else if("del".equals(type)){
						return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
					}else if("edit".equals(type)){
						return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
					}else if("cha".equals(type)){
						return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
					}
				}
			}else{
				if(!readMenuButton(menuList.get(i).getSubMenu(),menuUrl,session,USERNAME,type)){//继续排查其子菜单
					return false;
				};
			}
				
		}
		return false;
	}
	
	
	/**获取当前登录的用户名
	 * @return
	 */
	public static String getUsername(){
		return getSession().getAttribute(Const.SESSION_USERNAME).toString();
	}
	
	/**获取当前按钮权限(增删改查)
	 * @return
	 */
	public static Map<String, String> getHC(){
		return (Map<String, String>)getSession().getAttribute(getUsername() + Const.SESSION_QX);
	}
	
	/**
	 * shiro管理的session
	 * @return
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
}
