//
//  SearchTutorTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/9.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SearchTutorTableViewCell : UITableViewCell

@property (nonatomic, strong) UIImageView *searchIV;
@property (nonatomic, strong) UILabel *searchNameL;

@property (nonatomic, strong) UILabel *topicForSearchL;
@property (nonatomic, strong) UILabel *tagForSearchL;
//@property (nonatomic, strong) UILabel *schoolForSearchL;
//@property (nonatomic, strong) UILabel *positionForSearchL;

@property (nonatomic, strong) UILabel *hasSeenForSearchL;
@property (nonatomic, strong) UILabel *clickNumberForSearchL;

@end
