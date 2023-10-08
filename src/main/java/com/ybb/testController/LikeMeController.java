package com.ybb.testController;

import com.ybb.service.BackupInformationService;
import com.ybb.service.likeMeService;
import com.ybb.service.menuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("公众号测试")
@RestController
public class LikeMeController {
    @Resource
    private likeMeService lms;
    @Resource
    WxMpService wxMpService;
    @Resource
    private menuService mu;
    @Resource
    BackupInformationService backupInformationService;
    @ApiOperation("获取当前关注的人员列表")
    @GetMapping("/love")
    public WxMpUserList getList() throws WxErrorException {
        WxMpUserList likeList = lms.getLikeList();
        return likeList;
    }
    @ApiOperation("获取关注我的用户详情信息")
    @GetMapping("/userPrivacy")
    public WxMpUser getUser(@ApiParam("用户id") String openId){
       return lms.getUserMessage(openId);
    }
    @RequestMapping("/addMenu")
    public void addMenu() throws WxErrorException {
        mu.addMenu();
    }
    @RequestMapping("/delMenu")
    public void delBtn() throws WxErrorException {
        mu.delBtn();
    }
    @RequestMapping("/getMenu")
    public void getMenu() throws WxErrorException {
        WxMpMenu wxMenu = wxMpService.getMenuService().menuGet();
        System.out.println("餐单名为"+wxMenu.getMenu());
    }
    @GetMapping("/autoPassLoveMe")
    public void autoPassLoveMe(){
        backupInformationService.autoPassLoveMe();
    }
}
