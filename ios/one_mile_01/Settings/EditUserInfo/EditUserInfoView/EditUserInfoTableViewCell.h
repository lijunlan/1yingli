//
//  EditUserInfoTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol EditInterface <NSObject>

@optional
-(void)changeTelNum;
-(void)changeEmail;

@end

@interface EditUserInfoTableViewCell : UITableViewCell

@property (nonatomic, strong) UILabel *userInfoL;
@property (nonatomic, strong) UITextField *userInfoTF;

@property (nonatomic, strong) UIButton *telOrEmBT;

@property (nonatomic, assign) id<EditInterface>myDelegate;

@end
