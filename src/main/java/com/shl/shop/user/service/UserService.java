package com.shl.shop.user.service;

import com.shl.shop.user.dao.*;
import com.shl.shop.user.model.*;
import com.shl.shop.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FavoriteSellerDao favoriteSellerDao;

    @Autowired
    private FavoriteProductDao favoriteProductDao;

    @Autowired
    private AddressDao addressDao;


//    用户注册
    public User userRegister(User user){
        return userDao.save(user);
    }

//    更新用户
    public User updateUser(User user){
        return userDao.save(user);
    }

//    通过用户Id查询用户
    public User findByUserId(Integer userId){
        return userDao.findOne(userId);
    }

 //    通过用户名查询用户
    public User findByUserName(String userName){
        return userDao.findByUserName(userName);
    }

//    通过用户名和密码查询用户
    public User findByUserNameAndPassword(String userName,String password){
        return userDao.findByUserNameAndPassword(userName,password);
    }

//    删除用户
    public void deleteByUserId(Integer userId) throws Exception {
        if (userDao.findOne(userId) == null)
            throw new Exception("用户不存在！");
        else {
            addressDao.deleteByUserId(userId);
            favoriteSellerDao.deleteByUserId(userId);
            favoriteProductDao.deleteByUserId(userId);
            userDao.delete(userId);
        }
    }

//    商家注册
    public User sellerRegister(User seller){
        return userDao.save(seller);
    }

//    更新商家
    public User updateSeller(User seller){
        return userDao.save(seller);
    }

//    通过商家Id查询商家
    public User findBySellerId(Integer sellerId){
        return userDao.findOne(sellerId);
    }

//    通过商家名查询商家
    public User findBySellerName(String sellerName){
        return userDao.findByUserName(sellerName);
    }

//    通过商家名和密码查询商家
    public User findBySellerNameAndPassword(String sellerName,String password){
        return userDao.findByUserNameAndPassword(sellerName,password);
    }

//    删除商家
    public void deleteSeller(Integer sellerId) throws Exception{
        if(userDao.findOne(sellerId) == null)
            throw new Exception("商家不存在！");
        else {
            favoriteSellerDao.deleteBySellerId(sellerId);
            userDao.delete(sellerId);
        }
    }

//    用户收藏商家
    public FavoriteSeller collectSeller(Integer userId,Integer sellerId){
        FavoriteSeller favoriteSeller = new FavoriteSeller();
        favoriteSeller.setUserId(userId);
        favoriteSeller.setSellerId(sellerId);
        favoriteSeller.setCollectTime(new Date());
        return favoriteSellerDao.save(favoriteSeller);
    }

//    获取收藏的商家记录
    public FavoriteSeller getFavoriteSeller(Integer userId,Integer sellerId){
        return favoriteSellerDao.findByUserIdAndSellerId(userId,sellerId);
    }

//    删除收藏的商家记录
    public void deleteFavoriteSeller(FavoriteSeller favoriteSeller){
        favoriteSellerDao.delete(favoriteSeller);
    }

//    分页查询收藏的所有商家
   public Page<UserVo> showAllFavoriteSeller(Integer userId, Integer pageNumber, Integer pageSize){
//        Specification<FavoriteSeller> sellerSpecification = new Specification<FavoriteSeller>() {
//            @Override
//            public Predicate toPredicate(Root<FavoriteSeller> root,
//                                         CriteriaQuery<?> criteriaQuery,
//                                         CriteriaBuilder criteriaBuilder) {
//                Path<Integer> _userId = root.get("userId");
//                Predicate _key = criteriaBuilder.equal(_userId,userId);
//                return criteriaBuilder.and(_key);
//            }
//        };
        pageNumber = (pageNumber == null ? 1 : pageNumber-1);
        pageSize = (pageSize == null ? 1 : pageSize);
        Pageable pageable = new PageRequest(pageNumber,pageSize, Sort.Direction.DESC,"collectTime");
        Page<FavoriteSeller> favoriteSellerList = favoriteSellerDao.findAllByUserId(userId,pageable);
        List<UserVo> userVoList = new ArrayList<>();
        favoriteSellerList.forEach(favoriteSeller -> {
            UserVo userVo = new UserVo();
            userVo.setUserId(userId);
            userVo.setSeller(userDao.findOne(favoriteSeller.getSellerId()));
            userVo.setCollectTime(favoriteSeller.getCollectTime());
            userVoList.add(userVo);
        });
        Page page = new PageImpl(userVoList,pageable,userVoList.size());
        return page;
   }

//    用户收藏商品
    public FavoriteProduct collectProduct(Integer userId, Integer productId){
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setUserId(userId);
        favoriteProduct.setProductId(productId);
        favoriteProduct.setCollectTime(new Date());
        return favoriteProductDao.save(favoriteProduct);
    }

//    获取收藏的商品记录
    public FavoriteProduct getFavoriteProduct(Integer userId,Integer productId){
        return favoriteProductDao.findByUserIdAndProductId(userId,productId);
    }

//    删除收藏的商品记录
    public void deleteFavoriteProduct(FavoriteProduct favoriteProduct){
        favoriteProductDao.delete(favoriteProduct);
    }

//    增加收货地址
    public Address addAddress(Address address){
        return addressDao.save(address);
    }

//    更新收货地址
    public Address updateAddress(Address address){
        return addressDao.save(address);
    }

//    删除收货地址
    public void deleteAddress(Address address){
            addressDao.delete(address);
    }

//    通过收货地址Id查询收货地址
    public Address findByAddressId(Integer addressId){
        return addressDao.findByAddressId(addressId);
    }

//    查询用户所有收货地址
   public List<Address> getAllAdress(Integer userId){
        return addressDao.findAllByUserId(userId);
   }
}

