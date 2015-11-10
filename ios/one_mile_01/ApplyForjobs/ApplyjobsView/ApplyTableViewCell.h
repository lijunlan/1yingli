//
//  ApplyTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ApplyTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *applyTutorIV;
@property (nonatomic, strong) UILabel *applyTutorNameL;

@property (nonatomic, strong) UILabel *topicForApplyL;
@property (nonatomic, strong) UILabel *schoolForApplyL;
@property (nonatomic, strong) UILabel *positionForApplyL;
@property (nonatomic, strong) UILabel *simInfoForApplyL;

@property (nonatomic, strong) UILabel *hasSeenForApplyL;
@property (nonatomic, strong) UILabel *clickNumberForApplyL;

@end
