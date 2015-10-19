//
//  talkTableViewCell.h
//  one_mile_01
//
//  Created by 王进帅 on 15/8/25.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "collectionModel.h"
#import <UIImageView+WebCache.h>
@interface talkTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *tutorIV;
@property (nonatomic, strong) UILabel *tutorNameL;

@property (nonatomic, strong) UILabel *topicL;
@property (nonatomic, strong) UILabel *schoolL;
@property (nonatomic, strong) UILabel *positionL;
@property (nonatomic, strong) UILabel *simInfoForCollectionL;

@property (nonatomic, strong) UILabel *tutorPriceL;

@end
