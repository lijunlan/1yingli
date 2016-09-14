//
//  AFNConnect.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/28.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AFNetworking/AFNetworking.h>

@interface AFNConnect : NSObject

+ (void) AFNConnectWithUrl:(NSString *)urlStr withBodyData:(NSData *)data connectBlock:(void(^)(id data))myBlock;

//导师列表
+ (NSData *) createDataForTeacherList:(NSString *)currentID withdownUpdata:(BOOL)downUpdata withisFirst:(BOOL)isFirst;

//创业 求职 校园 猎奇
+ (NSData *) createDataForList:(NSString *)tip withNextID:(NSString *)currentID withdownUpdata:(BOOL)downUpdata withisFirst:(BOOL)isFirst;

//导师详情
+ (NSData *) createDataForTeacherDetail:(NSString *)teacherID;

//通知消息
+ (NSData *) createDataForNotification:(NSString *)userID withPage:(NSString *)pageNum;

//评价订单
+ (NSData *) createDataForOrderComments:(NSString *)orderID withTeacherID:(NSString *)teacherID withScore:(NSInteger)score withContents:(NSString *)contents withUserID:(NSString *)userID;

//获取导师评价列表
+ (NSData *) createDataForTeacherCommentList:(NSString *)teacherID withPage:(NSString *)pageNum;

//获取用户评价列表 (kind = 1 用户自己 2 给我的评价)
+ (NSData *) createDataForStuCommentList:(NSString *)userID withPage:(NSString *)pageNum withKind:(NSString *)kind;

//登陆
+ (NSData *) createDataForLogin:(NSString *)username withPwd:(NSString *)password;

//微信登录
+ (NSData *) createDataForThirdWechatLogin:(NSString *)code;

//微博登录
+ (NSData *) createDataForThirdWeiboLogin:(NSString *)accessToken withUID:(NSString *)uid;

//注册
+ (NSData *) createDataForRegister:(NSString *)username withPwd:(NSString *)password withCheckNo:(NSString *)checkNo withNickName:(NSString *)nickName;

//获取验证码
+ (NSData *) createDataForCheckNo:(NSString *)username;

//更改手机
+ (NSData *) createDataForChangePhoneNo:(NSString *)userID withCheckNo:(NSString *)checkNo withPhone:(NSString *)phone;

//更改邮箱
+ (NSData *) createDataForChangeEmail:(NSString *)userID withCheckNo:(NSString *)checkNo withEmail:(NSString *)email;

//更改密码
+ (NSData *) createDataForChangePass:(NSString *)password withOldPassword:(NSString *)oldPassword withUid:(NSString *)uid;

//个人详细信息
+ (NSData *) createDataForPersonalInfo:(NSString *)userID;

//修改个人信息
+ (NSData *) createDataForChangeInfo:(NSString *)userID withNickname:(NSString *)nickName withName:(NSString *)name withAddress:(NSString *)address withEmail:(NSString *)email withResume:(NSString *)resume;

//导师修改导师主页
+ (NSData *) createDataForChangeTeaInfo:(NSString *)userID withTeacherID:(NSString *)teacherID withperWeek:(NSString *)timeperweek withPrice:(NSString *)price withTalkway:(NSString *)talkWay withTime:(NSString *)time withAddress:(NSString *)address;

//上传的图片保存到本地
+ (NSString *) createDataForSavePhoto:(UIImage *)currentImage withName:(NSString *)imageName;

//保存图片
+ (NSData *) createDataForSavePhoto:(NSString *)userID withiconUrl:(NSString *)iconUrl;

//搜索
+ (NSData *) createDataForSearchTutor:(NSString *)keyWords andFilter:(NSString *)filter andPage:(NSString *)index;

//查看是否喜欢该导师
+ (NSData *) createDataForisLikeTutor:(NSString *)userID andTutorID:(NSString *)teacherID;

//取消喜欢导师
+ (NSData *) createDataForCancelTutor:(NSString *)userID andTutorID:(NSString *)teacherID;

//添加喜欢导师
+ (NSData *) createDataForCollectTutor:(NSString *)userID andTutorID:(NSString *)teacherID;

//心愿单
+ (NSData *) createDataForCollectionLoving:(NSString *)userID page:(NSString *)pageID;

//评价自己
+ (NSData *) createDataForIntro:(NSString *)userID;

//成为导师
+ (NSData *) createDataForTobeTutor:(NSString *)userID withApplication:(NSString *)application;

/* **********************************
 *订单相关
 ************************************ */

//创建订单
+(NSData *) createDataForCreateOrder:(NSString *)userID withQuestion:(NSString *)question withIntro:(NSString *)introduce withTeacherID:(NSString *)teacherID withSelectTime:(NSString *)selectTime withName:(NSString *)name withPhone:(NSString *)phone withEmail:(NSString *)email withContact:(NSString *)contact;

//导师拒绝接受服务
+(NSData *) createDataForTutorRefuse:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID withrefuseReason:(NSString *)reason;

//导师双方已经约定好时间
+(NSData *) createDataForEnsureTime:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID withokTime:(NSString *)oktime;

/*
 *导师不同意退款disagreeOrder  
 *导师同意退款agreeOrder 
 *导师接受服务订单acceptOrder 
 *导师确认双方没有约定好时间selectNoTime
 */
+(NSData *) createDataForTutorOrderAction:(NSString *)method withUserID:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID;

/*
 *用户满意服务satisfyOrder 
 *用户不满意服务dissatisfyOrder 
 *用户取消订单(未支付的时候)cancelOrder 
 *用户取消订单(用户已经支付，导师尚未接受订单) cancelOrderAfterPay
 *用户取消订单(导师已经接受订单，但双方还没有商量好服务时间) cancelOrderAfterAccept
 *用户取消订单(双方已经确定好时间) cancelOrderAfterEnsure
 */
+(NSData *) createDataForClientOrderAction:(NSString *)method withUserID:(NSString *)userID withOrderID:(NSString *)orderID;

//学员订单详情
+ (NSData *) createDataForClientOrderDetail:(NSString *)userID withPage:(NSString *)page;
+ (NSData *) createDataForClientOrderDetail:(NSString *)userID withState:(NSString *)state withPage:(NSString *)page;

//老师订单详情
+ (NSData *) createDataForTutorOrderDetail:(NSString *)userID withTeacherID:(NSString *)teacherID withPage:(NSString *)page;
+ (NSData *) createDataForTutorOrderDetail:(NSString *)userID withTeacherID:(NSString *)teacherID withState:(NSString *)state withPage:(NSString *)page;

/* ****************************
 * 评论
 ****************************** */
+(NSData *) createDataForJudgement:(NSString *)style withMethod:(NSString *)method withOrderID:(NSString *)orderID withTeacherID:(NSString *)teacherID withScore:(NSInteger)score withContent:(NSString *)content withUserID:(NSString *)userID;

@end
