//
//  ApplyTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ApplyTableViewCell.h"

@interface ApplyTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation ApplyTableViewCell

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
    
    self.applyTutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.applyTutorIV.backgroundColor = [UIColor yellowColor];
    _applyTutorIV.layer.masksToBounds = YES;
    _applyTutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_applyTutorIV];
    
    self.applyTutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_applyTutorIV.frame.origin.x, _applyTutorIV.frame.origin.y + _applyTutorIV.frame.size.height, _applyTutorIV.frame.size.width, 40)];
    //self.applyTutorNameL.backgroundColor = [UIColor yellowColor];
    self.applyTutorNameL.text = @"王雅蓉";
    self.applyTutorNameL.textAlignment = NSTextAlignmentCenter;
    self.applyTutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.applyTutorNameL.font = [UIFont systemFontOfSize:17.0f];
    self.applyTutorNameL.font = [UIFont boldSystemFontOfSize:18.0f];
    self.applyTutorNameL.numberOfLines = 2;
    [self.contentView addSubview:_applyTutorNameL];
    
    //标题
    self.topicForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(_applyTutorIV.frame.origin.x + _applyTutorIV.frame.size.width + 10, _applyTutorIV.frame.origin.y, WIDTH - 10 - _applyTutorIV.frame.size.width - 40, 50)];
    //self.topicForApplyL.backgroundColor = [UIColor yellowColor];
    //self.topicForApplyL.font = [UIFont systemFontOfSize:15.0f];
    self.topicForApplyL.numberOfLines = 2;
    self.topicForApplyL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicForApplyL];
    
    self.simInfoForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForApplyL.frame.origin.x, _topicForApplyL.frame.origin.y + _topicForApplyL.frame.size.height + 7, _topicForApplyL.frame.size.width, _topicForApplyL.frame.size.height - 25)];
    self.simInfoForApplyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.simInfoForApplyL.font = [UIFont systemFontOfSize:15.0f];
    [self.contentView addSubview:_simInfoForApplyL];
//    //学校
//    self.schoolForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForApplyL.frame.origin.x, _topicForApplyL.frame.origin.y + _topicForApplyL.frame.size.height , _topicForApplyL.frame.size.width - 30, _topicForApplyL.frame.size.height - 25)];
//    //self.schoolForApplyL.backgroundColor = [UIColor yellowColor];
//    self.schoolForApplyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.schoolForApplyL.font = [UIFont systemFontOfSize:15.0f];
//    [self.contentView addSubview:_schoolForApplyL];
//    
//    //职位
//    self.positionForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolForApplyL.frame.origin.x, _schoolForApplyL.frame.origin.y + _schoolForApplyL.frame.size.height, _schoolForApplyL.frame.size.width, _schoolForApplyL.frame.size.height)];
//    self.positionForApplyL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionForApplyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_positionForApplyL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicForApplyL.frame.origin.x, _simInfoForApplyL.frame.origin.y + _simInfoForApplyL.frame.size.height + 5, _topicForApplyL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    //见过人数
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _applyTutorNameL.frame.origin.y + 7, _applyTutorNameL.frame.size.height - 20, _applyTutorNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, hasSeenImageView.frame.origin.y, (_topicForApplyL.frame.size.width - 40) / 2.0 - 10, hasSeenImageView.frame.size.height)];
    //self.hasSeenForApplyL.backgroundColor = [UIColor yellowColor];
    self.hasSeenForApplyL.text = @" 9人见过";
    if (iPhone5 || iPhone4s) {
        self.hasSeenForApplyL.font = [UIFont systemFontOfSize:12.0f];
        //self.hasSeenForApplyL.font = [UIFont boldSystemFontOfSize:11.5f];
    } else {
        self.hasSeenForApplyL.font = [UIFont systemFontOfSize:14.0f];
        //self.hasSeenForApplyL.font = [UIFont boldSystemFontOfSize:13.5f];
    }
    self.hasSeenForApplyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenForApplyL];
    
    self.clickNumberForApplyL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenForApplyL.frame.origin.x + _hasSeenForApplyL.frame.size.width, _hasSeenForApplyL.frame.origin.y, aLine.frame.size.width - _hasSeenForApplyL.frame.size.width - hasSeenImageView.frame.size.width, _hasSeenForApplyL.frame.size.height)];
    //self.clickNumberForApplyL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberForApplyL.text = @"1444/ 次";
    self.clickNumberForApplyL.textAlignment = NSTextAlignmentRight;
    self.clickNumberForApplyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    if (iPhone5 || iPhone4s) {
        self.clickNumberForApplyL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.clickNumberForApplyL.font = [UIFont systemFontOfSize:14.0f];
    }
    [self.contentView addSubview:_clickNumberForApplyL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
