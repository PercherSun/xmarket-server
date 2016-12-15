package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.jcala.xmarket.server.conf.ApiConf;
import me.jcala.xmarket.server.entity.configuration.TradeType;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api("跟用户信息有关的api")
@RestController
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户登录并获取token",response = Result.class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=200,message="登录成功,用户名错误,密码错误"),
            @ApiResponse(code=500,message="服务器异常"),
            @ApiResponse(code=400,message="请求参数不合法")
    })
    @PostMapping(value = ApiConf.auth,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(@RequestParam String username,@RequestParam String password){
        return userService.loginAndGetToken(username,password);
    }

    @ApiOperation(value = "用户注册",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = ApiConf.register,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(@RequestParam String username,@RequestParam String password){
     return userService.register(username,password);
   }

    @ApiOperation(value = "设置用户学校和电话号码",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = ApiConf.register_next,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> registerNext(@PathVariable("userId") String id,@RequestParam String phone,@RequestParam String school){
        return userService.updatePhoneSchool(id,phone,school);
    }


    @ApiOperation(value = "修改用户密码",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = ApiConf.update_user_pass, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUserPassword(@PathVariable("userId")String id,@RequestParam String oldPass,@RequestParam String newPass)
            throws RuntimeException{
        return userService.updatePassword(id,oldPass,newPass);
    }
    @ApiOperation(value = "修改用户头像",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = ApiConf.update_user_avatar,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUserAvatar(@PathVariable("userId")String id, HttpServletRequest request)
            throws Exception{
      return userService.updateAvatar(id,request);
    }

    @ApiOperation(value = "获取用户志愿队信息",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = ApiConf.get_user_team,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainUserTeam(@PathVariable("userId")String id) throws RuntimeException{
        // TODO: 16-12-09  没有实现用户的志愿队列表获取代码
        return null;
    }

    @ApiOperation(value = "获取商品列表;根据kind的值获取不同类型列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = ApiConf.get_user_trades,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTrades(@PathVariable("userId") String userId,@RequestParam int kind){
        if (kind==0){
            return RespFactory.INSTANCE().paramsError();
        }
        switch (kind){
            case 1:return userService.getTrades(TradeType.DONATE,userId);
            case 2:return userService.getTrades(TradeType.SOLD,userId);
            case 3:return userService.getTrades(TradeType.BOUGHT,userId);
            case 4:return userService.getTrades(TradeType.SELL,userId);
            case 5:return userService.getTrades(TradeType.TO_BE_CONFIRMED, userId);
            default:return RespFactory.INSTANCE().paramsError();
        }
    }

}
