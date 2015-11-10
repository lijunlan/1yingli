//
//  CampusTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "CampusTableViewCell.h"

@interface CampusTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation CampusTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    bgImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, COMMONCELLHEIGHT)];
    bgImageView.image = [UIImage imageNamed:@"kuangjia.png"];
    [self.contentView addSubview:bgImageView];
    
    self.campusTutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.campusTutorIV.backgroundColor = [UIColor yellowColor];
    _campusTutorIV.layer.masksToBounds = YES;
    _campusTutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_campusTutorIV];
    
    self.campusTutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_campusTutorIV.frame.origin.x, _campusTutorIV.frame.origin.y + _campusTutorIV.frame.size.height, _campusTutorIV.frame.size.width, 40)];
    //self.campusTutorNameL.backgroundColor = [UIColor yellowColor];
    self.campusTutorNameL.text = @"王雅蓉";
    self.campusTutorNameL.textAlignment = NSTextAlignmentCenter;
    self.campusTutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.campusTutorNameL.font = [UIFont systemFontOfSize:17.0f];
    self.campusTutorNameL.font = [UIFont boldSystemFontOfSize:18.0f];
    self.campusTutorNameL.numberOfLines = 2;
    [self.contentView addSubview:_campusTutorNameL];
    
    //标题
    self.topicForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(_campusTutorIV.frame.origin.x + _campusTutorIV.frame.size.width + 10, _campusTutorIV.frame.origin.y, WIDTH - 10 - _campusTutorIV.frame.size.width - 40, 50)];
    //self.topicForCampusL.backgroundColor = [UIColor yellowColor];
    //self.topicForCampusL.font = [UIFont systemFontOfSize:15.0f];
    self.topicForCampusL.numberOfLines = 2;
    self.topicForCampusL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicForCampusL];
    
    self.simInfoForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForCampusL.frame.origin.x, _topicForCampusL.frame.origin.y + _topicForCampusL.frame.size.height + 7, _topicForCampusL.frame.size.width, _topicForCampusL.frame.size.height - 25)];
    self.simInfoForCampusL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.simInfoForCampusL.font = [UIFont systemFontOfSize:15.0f];
    [self.contentView addSubview:_simInfoForCampusL];
//    //学校
//    self.schoolForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForCampusL.frame.origin.x, _topicForCampusL.frame.origin.y + _topicForCampusL.frame.size.height, _topicForCampusL.frame.size.width - 30, _topicForCampusL.frame.size.height - 25)];
//    //self.schoolForCampusL.backgroundColor = [UIColor yellowColor];
//    self.schoolForCampusL.font = [UIFont systemFontOfSize:15.0f];
//    self.schoolForCampusL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_schoolForCampusL];
//    
//    //职位
//    self.positionForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolForCampusL.frame.origin.x, _schoolForCampusL.frame.origin.y + _schoolForCampusL.frame.size.height, _schoolForCampusL.frame.size.width, _schoolForCampusL.frame.size.height)];
//    self.positionForCampusL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionForCampusL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_positionForCampusL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicForCampusL.frame.origin.x, _simInfoForCampusL.frame.origin.y + _simInfoForCampusL.frame.size.height + 7, _topicForCampusL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _campusTutorNameL.frame.origin.y + 8, _campusTutorNameL.frame.size.height - 20, _campusTutorNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, _campusTutorNameL.frame.origin.y, (_topicForCampusL.frame.size.width - 40) / 2.0 - 10, _campusTutorNameL.frame.size.height)];
    //self.hasSeenForCampusL.backgroundColor = [UIColor yellowColor];
    self.hasSeenForCampusL.text = @" 9人见过";
    if (iPhone5 || iPhone4s) {
        self.hasSeenForCampusL.font = [UIFont systemFontOfSize:12.0f];
        //self.hasSeenForCampusL.font = [UIFont boldSystemFontOfSize:11.5f];
    } else {
        self.hasSeenForCampusL.font = [UIFont systemFontOfSize:14.0f];
        //self.hasSeenForCampusL.font = [UIFont boldSystemFontOfSize:13.5f];
    }
    self.hasSeenForCampusL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenForCampusL];
    
    self.clickNumberForCampusL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenForCampusL.frame.origin.x + _hasSeenForCampusL.frame.size.width, _hasSeenForCampusL.frame.origin.y, aLine.frame.size.width - _hasSeenForCampusL.frame.size.width - hasSeenImageView.frame.size.width, _hasSeenForCampusL.frame.size.height)];
    //self.clickNumberForCampusL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberForCampusL.text = @"1444/ 次";
    self.clickNumberForCampusL.textAlignment = NSTextAlignmentRight;
    self.clickNumberForCampusL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    if (iPhone5 || iPhone4s) {
        self.clickNumberForCampusL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.clickNumberForCampusL.font = [UIFont systemFontOfSize:14.0f];
    }
    [self.contentView addSubview:_clickNumberForCampusL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
