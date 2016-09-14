//
//  ShareTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ShareTableViewCell.h"

@interface ShareTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation ShareTableViewCell

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
    
    self.shareTutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.shareTutorIV.backgroundColor = [UIColor yellowColor];
    _shareTutorIV.layer.masksToBounds = YES;
    _shareTutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_shareTutorIV];
    
    self.shareTutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_shareTutorIV.frame.origin.x, _shareTutorIV.frame.origin.y + _shareTutorIV.frame.size.height, _shareTutorIV.frame.size.width, 40)];
    //self.shareTutorNameL.backgroundColor = [UIColor yellowColor];
    self.shareTutorNameL.text = @"王雅蓉";
    self.shareTutorNameL.textAlignment = NSTextAlignmentCenter;
    self.shareTutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.shareTutorNameL.font = [UIFont systemFontOfSize:17.0f];
    self.shareTutorNameL.font = [UIFont boldSystemFontOfSize:18.0f];
    self.shareTutorNameL.numberOfLines = 2;
    [self.contentView addSubview:_shareTutorNameL];
    
    //标题
    self.topicForShareL = [[UILabel alloc] initWithFrame:CGRectMake(_shareTutorIV.frame.origin.x + _shareTutorIV.frame.size.width + 10, _shareTutorIV.frame.origin.y, WIDTH - 10 - _shareTutorIV.frame.size.width - 40, 50)];
    //self.topicForShareL.backgroundColor = [UIColor yellowColor];
    //self.topicForShareL.font = [UIFont systemFontOfSize:15.0f];
    self.topicForShareL.text = @"聊一聊风险投资是怎么一回事";
    self.topicForShareL.numberOfLines = 2;
    [self.contentView addSubview:_topicForShareL];
    
    self.simInfoForShareL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForShareL.frame.origin.x, _topicForShareL.frame.origin.y + _topicForShareL.frame.size.height + 7, _topicForShareL.frame.size.width, _topicForShareL.frame.size.height - 25)];
    self.simInfoForShareL.font = [UIFont systemFontOfSize:15.0f];
    self.simInfoForShareL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_simInfoForShareL];
//    //学校
//    self.schoolForShareL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForShareL.frame.origin.x, _topicForShareL.frame.origin.y + _topicForShareL.frame.size.height, _topicForShareL.frame.size.width - 30, _topicForShareL.frame.size.height - 25)];
//    //self.schoolForShareL.backgroundColor = [UIColor yellowColor];
//    self.schoolForShareL.font = [UIFont systemFontOfSize:15.0f];
//    self.schoolForShareL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_schoolForShareL];
//    
//    //职位
//    self.positionForShareL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolForShareL.frame.origin.x, _schoolForShareL.frame.origin.y + _schoolForShareL.frame.size.height, _schoolForShareL.frame.size.width, _schoolForShareL.frame.size.height)];
//    self.positionForShareL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionForShareL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_positionForShareL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicForShareL.frame.origin.x, _simInfoForShareL.frame.origin.y + _simInfoForShareL.frame.size.height + 7, _topicForShareL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _shareTutorNameL.frame.origin.y + 8, _shareTutorNameL.frame.size.height - 20, _shareTutorNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenForShareL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, _shareTutorNameL.frame.origin.y, (_topicForShareL.frame.size.width - 40) / 2.0 - 10, _shareTutorNameL.frame.size.height)];
    //self.hasSeenForShareL.backgroundColor = [UIColor yellowColor];
    self.hasSeenForShareL.text = @" 9人见过";
    if (iPhone5 || iPhone4s) {
        self.hasSeenForShareL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.hasSeenForShareL.font = [UIFont systemFontOfSize:14.0f];
        //self.hasSeenForShareL.font = [UIFont boldSystemFontOfSize:13.5f];
    }
    self.hasSeenForShareL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenForShareL];
    
    self.clickNumberForShareL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenForShareL.frame.origin.x + _hasSeenForShareL.frame.size.width, _hasSeenForShareL.frame.origin.y, aLine.frame.size.width - _hasSeenForShareL.frame.size.width - hasSeenImageView.frame.size.width, _hasSeenForShareL.frame.size.height)];
    //self.clickNumberForShareL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberForShareL.text = @"1444/ 次";
    self.clickNumberForShareL.textAlignment = NSTextAlignmentRight;
    self.clickNumberForShareL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    if (iPhone5 || iPhone4s) {
        self.clickNumberForShareL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.clickNumberForShareL.font = [UIFont systemFontOfSize:14.0f];
    }
    [self.contentView addSubview:_clickNumberForShareL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
