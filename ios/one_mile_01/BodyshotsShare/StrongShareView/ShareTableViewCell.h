//
//  ShareTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ShareTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *shareTutorIV;
@property (nonatomic, strong) UILabel *shareTutorNameL;

@property (nonatomic, strong) UILabel *topicForShareL;
@property (nonatomic, strong) UILabel *schoolForShareL;
@property (nonatomic, strong) UILabel *positionForShareL;
@property (nonatomic, strong) UILabel *simInfoForShareL;

@property (nonatomic, strong) UILabel *hasSeenForShareL;
@property (nonatomic, strong) UILabel *clickNumberForShareL;

@end
