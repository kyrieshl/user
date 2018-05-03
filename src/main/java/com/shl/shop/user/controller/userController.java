package com.shl.shop.user.controller;

import com.shl.shop.user.enums.ResultEnum;
import com.shl.shop.user.model.*;
import com.shl.shop.user.result.Result;
import com.shl.shop.user.service.UserService;
import com.shl.shop.user.utils.ResultUtils;
import com.shl.shop.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class userController {

    @Autowired
    private UserService userService;

//    页面默认大小
    private Integer size = 10;

//    用户退出
    @GetMapping("/userQuit")
    public Result userQuit() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return ResultUtils.wrapResult(ResultEnum.SUCCESS, "退出成功！");
    }

//    用户登录
    @PostMapping("/userLogin")
    public Result userLogin(@RequestParam("userName") String userName,
                            @RequestParam("password") String password) {
        User user = userService.findByUserNameAndPassword(userName,password);
        if (user == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL, "用户名或密码错误！");
        else {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS, user);
        }
    }

////      用户注册
//    @PostMapping("/userRegister")
//    public Result userRegister(@Valid @ModelAttribute User user,
//                           @RequestParam("userImage") MultipartFile userImage) throws IOException {
////        String checkCode2 = (String) session.getAttribute("checkcode");
////        if (!checkCode1.equalsIgnoreCase(checkCode2))
////            return ResultUtils.wrapResult(ResultEnum.FAIL, "验证码错误！");
//        if (userService.findByUserName(user.getUserName()) != null) {
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "用户名已存在！");
//        }
//        else if (userImage.isEmpty()) {
//            System.out.println("头像为空");
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "头像不能为空！");
//        }
//        else{
//            boolean isImg = false;
//            String[] imgPostfix = {"gif","jpg","jpeg","bmp","png"};
//            for(String inst : imgPostfix){
//                if(userImage.getContentType().equalsIgnoreCase(inst)) {
//                    isImg = true;
//                    break;
//                }
//            }
//            if(!isImg)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像必须为图片格式！");
//            else if(userImage.getSize() > 10485760)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像大小不能超过10M");
//            else {
////                上传头像路径
//                String path = request.getServletContext().getRealPath("/images/");
////                上传头像名
//                String imageName = userImage.getOriginalFilename();
//                File filePath = new File(path,imageName);
//                System.out.println("上传头像路径："+path);
//                System.out.println("上传头像吗："+imageName);
//                System.out.println("filePath:"+filePath);
////                判断路径是否存在，若不存在则创建
//                if(!filePath.getParentFile().exists()){
//                    filePath.getParentFile().mkdirs();
//                }
////                将头像保存到目标文件中
//                userImage.transferTo(new File(path +File.separator + imageName));
//                user.setUserImage(path +File.separator + imageName);
////                设置买家标志位
//                user.setSellerFlag(false);
////                设置买家状态
//                user.setState(false);
//                return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.userRegister(user));
//            }
//        }
//    }


//      用户注册
    @PostMapping("/userRegister")
    public Result userRegister(@Valid @ModelAttribute User user){
//        String checkCode2 = (String) session.getAttribute("checkcode");
//        if (!checkCode1.equalsIgnoreCase(checkCode2))
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "验证码错误！");
        if (userService.findByUserName(user.getUserName()) != null) {
            return ResultUtils.wrapResult(ResultEnum.FAIL, "用户名已存在！");
        }
        else{
//       设置买家标志位
          user.setSellerFlag(false);
//       设置买家状态
          user.setState(false);
          user.setUserImage("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg");
          return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.userRegister(user));
        }
    }

//       获取用户资料
    @GetMapping("/getUser")
    public Result getUer(@RequestParam("userId") Integer userId){
        User user = userService.findByUserId(userId);
        if(user == null || user.getSellerFlag())
            return ResultUtils.wrapResult(ResultEnum.FAIL,"用户不存在！");
        else
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.findByUserId(userId));
    }

//      用户修改密码
    @PutMapping("/updateUserPassword")
    public Result updateUserPassword(@RequestParam("userId") Integer userId,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("rePassword") String rePassword) {
        if (!newPassword.equals(rePassword))
            return ResultUtils.wrapResult(ResultEnum.FAIL, "两次密码不一致！");
        else {
            User user = userService.findByUserId(userId);
            user.setPassword(newPassword);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateUser(user));
        }
    }

////    用户修改头像
//    @PutMapping("/updateUserImage")
//    public Result updateUserImage(@RequestParam("userId") Integer userId,
//                           @RequestParam("userImage") MultipartFile userImage) throws IOException {
//        if (userImage.isEmpty()) {
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "头像不能为空！");
//        }
//        else{
//            boolean isImg = false;
//            String[] imgPostfix = {"gif","jpg","jpeg","bmp","png"};
//            for(String inst : imgPostfix){
//                if(userImage.getContentType().equalsIgnoreCase(inst)) {
//                    isImg = true;
//                    break;
//                }
//            }
//            if(!isImg)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像必须为图片格式！");
//            else if(userImage.getSize() > 10485760)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像大小不能超过10M");
//            else {
////                上传头像路径
//                String path = request.getServletContext().getRealPath("/images/");
////                上传头像名
//                String imageName = userImage.getOriginalFilename();
//                File filePath = new File(path,imageName);
////                判断路径是否存在，若不存在则创建
//                if(!filePath.getParentFile().exists()){
//                    filePath.getParentFile().mkdirs();
//                }
////                将头像保存到目标文件中
//                userImage.transferTo(new File(path +File.separator + imageName));
//                User user = userService.findByUserId(userId);
//                user.setUserImage(path +File.separator + imageName);
//                return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateUser(user));
//            }
//        }
//    }

//    用户修改头像
    @PutMapping("/updateUserImage")
    public Result updateUserImage(@RequestParam("userId") Integer userId,
                                  @RequestParam("userImage") String userImage){
        User user = userService.findByUserId(userId);
        user.setUserImage(userImage);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateUser(user));
    }

//    删除用户
    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam("userId") Integer userId){
        User user = userService.findByUserId(userId);
        if(user == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"用户不存在！");
        else{
            userService.deleteUser(user);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功！");
        }
    }

//    商家退出
    @GetMapping("/sellerQuit")
    public Result sellerQuit(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("loginSeller");
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,"退出成功！");
    }

//    商家登录
    @PostMapping("/sellerLogin")
    public Result sellerLogin(@RequestParam("sellerName") String sellerName,
                           @RequestParam("password") String password) {
      User seller = userService.findBySellerNameAndPassword(sellerName,password);
      if (seller == null)
          return ResultUtils.wrapResult(ResultEnum.FAIL, "商家名或密码错误！");
       else {
          HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
          HttpSession session = request.getSession();
           session.setAttribute("loginSeller", seller);
           return ResultUtils.wrapResult(ResultEnum.SUCCESS, seller);
       }
    }

////    商家注册
//    @PostMapping("/sellerRegister")
//    public Result sellerRegister(@Valid @ModelAttribute User seller,
//                                 @RequestParam("sellerImage") MultipartFile sellerImage) throws IOException{
////        String checkCode2 = (String) session.getAttribute("checkcode");
////        if (!checkCode1.equalsIgnoreCase(checkCode2))
////            return ResultUtils.wrapResult(ResultEnum.FAIL, "验证码错误！");
//        if (userService.findBySellerName(seller.getUserName()) != null) {
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "用户名已存在！");
//        }
//        else if (sellerImage.isEmpty()) {
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "头像不能为空！");
//        }
//        else{
//            boolean isImg = false;
//            String[] imgPostfix = {"gif","jpg","jpeg","bmp","png"};
//            for(String inst : imgPostfix){
//                if(sellerImage.getContentType().equalsIgnoreCase(inst)) {
//                    isImg = true;
//                    break;
//                }
//            }
//            if(!isImg)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像必须为图片格式！");
//            else if(sellerImage.getSize() > 10485760)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像大小不能超过10M");
//            else {
////                上传头像路径
//                String path = request.getServletContext().getRealPath("/images/");
////                上传头像名
//                String imageName = sellerImage.getOriginalFilename();
//                File filePath = new File(path,imageName);
////                判断路径是否存在，若不存在则创建
//                if(!filePath.getParentFile().exists()){
//                    filePath.getParentFile().mkdirs();
//                }
////                将头像保存到目标文件中
//                sellerImage.transferTo(new File(path +File.separator + imageName));
//                seller.setUserImage(path +File.separator + imageName);
////                设置商家标志位
//                seller.setSellerFlag(true);
////                设置买家状态
//                seller.setState(false);
//                return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.sellerRegister(seller));
//            }
//        }
//    }

//      商家注册
    @PostMapping("/sellerRegister")
    public Result sellerRegister(@Valid @ModelAttribute User seller) {
//        String checkCode2 = (String) session.getAttribute("checkcode");
//        if (!checkCode1.equalsIgnoreCase(checkCode2))
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "验证码错误！");
        if (userService.findBySellerName(seller.getUserName()) != null) {
            return ResultUtils.wrapResult(ResultEnum.FAIL, "用户名已存在！");
        }
//     设置商家标志位
        seller.setSellerFlag(true);
//     设置买家状态
        seller.setState(false);
        seller.setUserImage("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg");
        return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.sellerRegister(seller));
    }

//    商家修改密码
    @PutMapping("/updateSellerPassword")
    public Result updateSellerPassword(@RequestParam("sellerId") Integer sellerId,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("rePassword") String rePassword){
        if (!newPassword.equals(rePassword))
            return ResultUtils.wrapResult(ResultEnum.FAIL, "两次密码不一致！");
        else {
            User seller = userService.findBySellerId(sellerId);
            seller.setPassword(newPassword);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateSeller(seller));
        }
    }

////    商家修改头像
//    @PutMapping("/updateSellerImage")
//    public Result updateSellerImage(@RequestParam("sellerId") Integer sellerId,
//                                    @RequestParam("sellerImage") MultipartFile sellerImage) throws IOException{
//        if (sellerImage.isEmpty()) {
//            return ResultUtils.wrapResult(ResultEnum.FAIL, "头像不能为空！");
//        }
//        else{
//            boolean isImg = false;
//            String[] imgPostfix = {"gif","jpg","jpeg","bmp","png"};
//            for(String inst : imgPostfix){
//                if(sellerImage.getContentType().equalsIgnoreCase(inst)) {
//                    isImg = true;
//                    break;
//                }
//            }
//            if(!isImg)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像必须为图片格式！");
//            else if(sellerImage.getSize() > 10485760)
//                return ResultUtils.wrapResult(ResultEnum.FAIL,"头像大小不能超过10M");
//            else {
////                上传头像路径
//                String path = request.getServletContext().getRealPath("/images/");
////                上传头像名
//                String imageName = sellerImage.getOriginalFilename();
//                File filePath = new File(path,imageName);
////                判断路径是否存在，若不存在则创建
//                if(!filePath.getParentFile().exists()){
//                    filePath.getParentFile().mkdirs();
//                }
////                将头像保存到目标文件中
//                sellerImage.transferTo(new File(path +File.separator + imageName));
//                User seller = userService.findBySellerId(sellerId);
//                seller.setUserImage(path +File.separator + imageName);
//                return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateSeller(seller));
//            }
//        }
//    }

//    商家修改头像
    @PutMapping("/updateSellerImage")
    public Result updateSellerImage(@RequestParam("sellerId") Integer sellerId,
                                    @RequestParam("sellerImage") String sellerImage) {
        User seller = userService.findBySellerId(sellerId);
        seller.setUserImage(sellerImage);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS, userService.updateSeller(seller));
    }

//    用户收藏商家
    @PostMapping("/collectSeller")
    public Result colletSeller(@RequestParam("userId") Integer userId,
                               @RequestParam("sellerId") Integer sellerId){
        FavoriteSeller favoriteSeller = userService.getFavoriteSeller(userId,sellerId);
        if(favoriteSeller == null){
            User seller = userService.findBySellerId(sellerId);
            if(seller == null)
                return ResultUtils.wrapResult(ResultEnum.FAIL,"商家不存在！");
            else
                return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.collectSeller(userId,sellerId));
        }
        else
            return ResultUtils.wrapResult(ResultEnum.FAIL,"该收藏记录已存在！");
    }

//    删除收藏的商家
    @DeleteMapping("/deleteFavoriteSeller")
    public Result deleteFavoriteSeller(@RequestParam("userId") Integer userId,
                                       @RequestParam("sellerId") Integer sellerId){
        FavoriteSeller favoriteSeller = userService.getFavoriteSeller(userId,sellerId);
        if(favoriteSeller == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"无此收藏记录!");
        else{
            userService.deleteFavoriteSeller(favoriteSeller);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功！");
        }
    }

//    分页查询收藏的商家
    @GetMapping("/showAllFavoriteSeller")
    public Result showAllFavoriteSeller(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "page",required = false) Integer page){
        page = (page == null ? 1:page);
        Page<UserVo> sellerList = userService.showAllFavoriteSeller(userId,page,size);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,sellerList.getContent());
    }

//    获取商家资料
    @GetMapping("/getSeller")
    public Result getSeller(@RequestParam("sellerId") Integer sellerId){
        User seller = userService.findBySellerId(sellerId);
        if(seller == null || !seller.getSellerFlag())
            return ResultUtils.wrapResult(ResultEnum.FAIL,"商家不存在！");
        else
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,seller);
    }

//    删除商家
    @DeleteMapping("/deleteSeller")
    public Result deleteSeller(@RequestParam("sellerId") Integer sellerId){
        User seller = userService.findByUserId(sellerId);
        if(seller == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"商家不存在！");
        else{
            userService.deleteSeller(seller);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功！");
        }
    }

//    用户收藏商品
    @PostMapping("/collectProduct")
    public Result collectProduct(@RequestParam("userId") Integer userId,
                                 @RequestParam("productId") Integer productId){
        FavoriteProduct favoriteProduct = userService.getFavoriteProduct(userId,productId);
        if(favoriteProduct == null)
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.collectProduct(userId,productId));
        else
            return ResultUtils.wrapResult(ResultEnum.FAIL,"收藏记录已存在！");
    }

//    删除收藏的商品
    @DeleteMapping("/deleteFavoriteProduct")
    public Result deleteFavoriteProduct(@RequestParam("userId") Integer userId,
                                        @RequestParam("productId") Integer productId){
        FavoriteProduct favoriteProduct = userService.getFavoriteProduct(userId,productId);
        if(favoriteProduct == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"该收藏记录不存在！");
        else{
            userService.deleteFavoriteProduct(favoriteProduct);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功！");
        }
    }

//    分页查询收藏的商品、获取单个商品----------------------

//    增加收货地址（单个用户最多十条收货地址）
    @PostMapping("/addAddress")
    public Result addAddress(@RequestParam("userId") Integer userId,
                             @Valid @ModelAttribute Address address){
        List<Address> addressList = userService.getAllAdress(userId);
        if(addressList.size() >= 10)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"收货地址数量已达上限！");
        else
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.addAddress(address));
    }

//    删除收货地址
    @DeleteMapping("/deleteAddress")
    public Result deleteAddress(@RequestParam("addressId") Integer addressId){
        Address address = userService.findByAddressId(addressId);
        if(address == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"该收货地址不存在！");
        else{
            userService.deleteAddress(address);
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功!");
        }
    }

//    查看收货地址详细信息
    @GetMapping("/getAddress")
    public Result getAddress(@RequestParam("addressId") Integer addressId){
        Address address = userService.findByAddressId(addressId);
        if(address == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"收货地址不存在！");
        else
            return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.findByAddressId(addressId));
    }

//    查询用户所有收货地址
    @GetMapping("/getAllAddress")
    public Result getAllAddress(@RequestParam("userId") Integer userId){
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,userService.getAllAdress(userId));
    }

//    删除用户足迹
    @DeleteMapping("/deleteFootprint")
    public Result deleteFootprint(@RequestParam("userId") Integer userId,
                                  @RequestParam("footprintId") Integer footprintId){
        if(userId == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"用户参数错误！");
        if(footprintId == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"足迹参数错误！");

        Footprint footprint = userService.findByFootprintId(footprintId);
        if(footprint == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"足迹不存在！");
        if(!footprint.getUserId().equals(userId))
            return ResultUtils.wrapResult(ResultEnum.FAIL,"用户或足迹参数错误！");

        userService.deleteByFootprint(footprint);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,"删除成功！");
    }

//    用户足迹列表
    @GetMapping("/getAllfootprint")
    public Result getAllFootprint(@RequestParam("userId") Integer userId,
                                    @RequestParam(value = "page",defaultValue = "1") Integer page){
        if(userId == null)
            return ResultUtils.wrapResult(ResultEnum.FAIL,"用户参数错误！");

        List<Footprint> footprintList = userService.getAllFootprint(userId,page,size);
        int count = footprintList.size();
        int totalPages = (int)Math.ceil((double) count / size);

        List<Object> footprintVoList = new ArrayList<>(footprintList.size());
        footprintList.forEach(footprint -> {
            Map<String,Object> c = new HashMap<>();
            c.put("id",footprint.getFootprintId());
            c.put("productId",footprint.getProductId());
            c.put("addTime",footprint.getAddTime());

            footprintVoList.add(c);
        });

        Map<String ,Object> result = new HashMap<>();
        result.put("footprintList",footprintVoList);
        result.put("totalPages",totalPages);
        return ResultUtils.wrapResult(ResultEnum.SUCCESS,result);
    }
}

