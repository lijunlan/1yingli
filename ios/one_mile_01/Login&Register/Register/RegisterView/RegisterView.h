//
//  RegisterView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/8/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol RegisterInterface <NSObject>

@optional
-(void)backToLogin;
-(void)registerOne:(NSString *)username withPwd:(NSString *)pass withCheckNo:(NSString *)check withNikName:(NSString *)nickname;

@end

@interface RegisterView : UIView

@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *iphoneLabel;
@property (nonatomic, strong) UILabel *setPassLabel;
@property (nonatomic, strong) UILabel *confirmLabel;
@property (nonatomic, strong) UITextField *nameTextFile;//昵称
@property (nonatomic, strong) UITextField *iphoneTextFile;//绑定手机
@property (nonatomic, strong) UITextField *passWordTextFile;//验证码
@property (nonatomic, strong) UITextField *setPassWordTextFile;//设置密码
@property (nonatomic, strong) UITextField *confirmTextFile;//确认密码

@property (nonatomic, assign) id<RegisterInterface>myDelegate;

@end
