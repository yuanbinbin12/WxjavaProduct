package com.ybb.service;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class menuService {
    @Resource
    WxMpService wxMpService;
    public void addMenu() throws WxErrorException {
        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton>  buttons = new ArrayList<>();
        WxMenuButton bt1 = new WxMenuButton();
        WxMenuButton bt2 = new WxMenuButton();
        WxMenuButton bt3 = new WxMenuButton();
        bt1.setType("view");
        bt1.setKey("61");
        bt1.setName("登录81");
        bt1.setUrl("http://www.yuanbinbin.cloud");
        bt2.setType("view");
        bt2.setKey("62");
        bt2.setName("登录82");
        bt2.setUrl("https://www.yuanbinbin.cloud");
        boolean add = buttons.add(bt1);

        boolean add1 = buttons.add(bt2);
        wxMenu.setButtons(buttons);
// 设置菜单
        String s = wxMpService.getMenuService().menuCreate(wxMenu);
        System.out.println(s);
    }
    public void delBtn() throws WxErrorException {
        WxMpMenu wxMenu = wxMpService.getMenuService().menuGet();
        System.out.println("餐单名为"+wxMenu.getMenu());
        wxMpService.getMenuService().menuDelete();
    }

}
