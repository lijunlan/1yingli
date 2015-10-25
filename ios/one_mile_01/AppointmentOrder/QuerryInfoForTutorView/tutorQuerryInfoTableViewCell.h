//
//  tutorQuerryInfoTableViewCell.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface tutorQuerryInfoTableViewCell : UITableViewCell
@property (nonatomic, strong) UIImageView *tutorIV;
@property (nonatomic, strong) UILabel *tutorNameL;

@property (nonatomic, strong) UILabel *topicL;
@property (nonatomic, strong) UILabel *positionL;

//支付金额
@property (nonatomic, strong) UILabel *moneyTeaL;
@property (nonatomic, strong) UILabel *alertTeaStateL;

@end
