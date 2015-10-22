//
//  editUsersNormalTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/15.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol editNormalInterface <NSObject>

@optional
-(void)beginEditText;
-(void)endEditText:(UITextField *)text;

@end

@interface editUsersNormalTableViewCell : UITableViewCell

@property (nonatomic, strong) UILabel *userNormL;
@property (nonatomic, strong) UITextField *userNormTF;

@property (nonatomic, assign) id<editNormalInterface>myDelegate;

@end
