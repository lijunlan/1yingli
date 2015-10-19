//
//  RegisterViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RegisterView.h"

@protocol regControllerInterface <NSObject>

@optional
-(void)sendPhoneOrEmail:(NSString *)phoneNumOrEmail;

@end

@interface RegisterViewController : UIViewController<RegisterInterface>

@property (nonatomic, assign) id<regControllerInterface>myDelegate;

@end
