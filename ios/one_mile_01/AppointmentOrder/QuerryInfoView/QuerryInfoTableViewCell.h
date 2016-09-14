//
//  QuerryInfoTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QuerryInfoTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *tutorIV;
@property (nonatomic, strong) UILabel *tutorNameL;

@property (nonatomic, strong) UILabel *topicL;
@property (nonatomic, strong) UILabel *selectTimeL;

//支付金额
@property (nonatomic, strong) UILabel *moneyL;
@property (nonatomic, strong) UILabel *alertStateL;

@end
