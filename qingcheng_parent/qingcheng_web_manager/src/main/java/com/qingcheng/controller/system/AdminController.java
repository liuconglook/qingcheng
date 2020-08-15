package com.qingcheng.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Reference
    private AdminService adminService;

    @GetMapping("/findAll")
    public List<Admin> findAll(){
        return adminService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Admin> findPage(int page, int size){
        return adminService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Admin> findList(@RequestBody Map<String,Object> searchMap){
        return adminService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Admin> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  adminService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Admin findById(Integer id){
        return adminService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return new Result();
    }
    
    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, Object> map){
        String password = (String) map.get("password");
        String newPassword = (String) map.get("newPassword");
        // 查询用户
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("loginName",name);
        searchMap.put("status", "1");
        List<Admin> list = adminService.findList(searchMap);
        if(list.size() == 0){
            throw new RuntimeException("用户不存在！");
        }
        // 验证旧密码
        if(!BCrypt.checkpw(password, list.get(0).getPassword())){
            throw new RuntimeException("旧密码错误！");
        }
        // 更新密码
        String salt = BCrypt.gensalt();
        adminService.updateByName(name, BCrypt.hashpw(newPassword, salt));
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        adminService.delete(id);
        return new Result();
    }


}
