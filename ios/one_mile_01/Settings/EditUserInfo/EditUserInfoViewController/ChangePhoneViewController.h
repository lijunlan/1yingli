//
//  ChangePhoneViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol changePhoneInterface <NSObject>

@optional
-(void)changeSuccess:(NSString *)phoneNum;

@end

@interface ChangePhoneViewController : UIViewController

@property (nonatomic, strong) UITextField *telNumTF;
@property (nonatomic, strong) UITextField *checkNoTF;

@property (nonatomic, strong) NSString *userIDforChange;

@property (nonatomic, weak) id<changePhoneInterface>myDelegate;

@end
