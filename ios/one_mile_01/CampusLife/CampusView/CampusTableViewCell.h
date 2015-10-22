//
//  CampusTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CampusTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *campusTutorIV;
@property (nonatomic, strong) UILabel *campusTutorNameL;

@property (nonatomic, strong) UILabel *topicForCampusL;
@property (nonatomic, strong) UILabel *schoolForCampusL;
@property (nonatomic, strong) UILabel *positionForCampusL;
@property (nonatomic, strong) UILabel *simInfoForCampusL;

@property (nonatomic, strong) UILabel *hasSeenForCampusL;
@property (nonatomic, strong) UILabel *clickNumberForCampusL;

@end
