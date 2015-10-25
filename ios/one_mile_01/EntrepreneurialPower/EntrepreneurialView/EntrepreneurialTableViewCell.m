//
//  EntrepreneurialTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EntrepreneurialTableViewCell.h"

@interface EntrepreneurialTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation EntrepreneurialTableViewCell

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
    
    self.entreTutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.entreTutorIV.backgroundColor = [UIColor yellowColor];
    _entreTutorIV.layer.masksToBounds = YES;
    _entreTutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_entreTutorIV];
    
    self.entreTutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_entreTutorIV.frame.origin.x, _entreTutorIV.frame.origin.y + _entreTutorIV.frame.size.height + 5, _entreTutorIV.frame.size.width, 40)];
    //self.entreTutorNameL.backgroundColor = [UIColor yellowColor];
    self.entreTutorNameL.text = @"王雅蓉";
    self.entreTutorNameL.textAlignment = NSTextAlignmentCenter;
    self.entreTutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.entreTutorNameL.font = [UIFont systemFontOfSize:17.0f];
    self.entreTutorNameL.font = [UIFont boldSystemFontOfSize:18.0f];
    self.entreTutorNameL.numberOfLines = 2;
    [self.contentView addSubview:_entreTutorNameL];
    
    //标题
    self.topicForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(_entreTutorIV.frame.origin.x + _entreTutorIV.frame.size.width + 10, _entreTutorIV.frame.origin.y, WIDTH - 10 - _entreTutorIV.frame.size.width - 40, 50)];
    //self.topicForEntreL.backgroundColor = [UIColor yellowColor];
    //self.topicForEntreL.font = [UIFont systemFontOfSize:15.0f];
    self.topicForEntreL.numberOfLines = 2;
    self.topicForEntreL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicForEntreL];
    
    self.simInfoForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForEntreL.frame.origin.x, _topicForEntreL.frame.origin.y + _topicForEntreL.frame.size.height + 7, _topicForEntreL.frame.size.width, _topicForEntreL.frame.size.height - 25)];
    self.simInfoForEntreL.font = [UIFont systemFontOfSize:15.0f];
    self.simInfoForEntreL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_simInfoForEntreL];
//    //学校
//    self.schoolForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForEntreL.frame.origin.x, _topicForEntreL.frame.origin.y + _topicForEntreL.frame.size.height, _topicForEntreL.frame.size.width - 30, _topicForEntreL.frame.size.height - 25)];
//    self.schoolForEntreL.font = [UIFont systemFontOfSize:15.0f];
//    self.schoolForEntreL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_schoolForEntreL];
//    
//    //职位
//    self.positionForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolForEntreL.frame.origin.x, _schoolForEntreL.frame.origin.y + _schoolForEntreL.frame.size.height, _schoolForEntreL.frame.size.width, _schoolForEntreL.frame.size.height)];
//    self.positionForEntreL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionForEntreL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_positionForEntreL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicForEntreL.frame.origin.x, _simInfoForEntreL.frame.origin.y + _simInfoForEntreL.frame.size.height + 7, _topicForEntreL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _entreTutorNameL.frame.origin.y + 9, _entreTutorNameL.frame.size.height - 20, _entreTutorNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, _entreTutorNameL.frame.origin.y, (_topicForEntreL.frame.size.width  - 30 )/ 2.0 - 10, _entreTutorNameL.frame.size.height)];
    //self.hasSeenForEntreL.backgroundColor = [UIColor yellowColor];
    self.hasSeenForEntreL.text = @" 9人见过";
    if (iPhone5 || iPhone4s) {
        self.hasSeenForEntreL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.hasSeenForEntreL.font = [UIFont systemFontOfSize:14.0f];
        //self.hasSeenForEntreL.font = [UIFont boldSystemFontOfSize:13.5f];
    }
    self.hasSeenForEntreL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenForEntreL];
    
    self.clickNumberForEntreL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenForEntreL.frame.origin.x + _hasSeenForEntreL.frame.size.width, _hasSeenForEntreL.frame.origin.y, aLine.frame.size.width - _hasSeenForEntreL.frame.size.width - hasSeenImageView.frame.size.width, _hasSeenForEntreL.frame.size.height)];
    //self.clickNumberForEntreL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberForEntreL.text = @"1444/ 次";
    self.clickNumberForEntreL.textAlignment = NSTextAlignmentRight;
    self.clickNumberForEntreL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    if (iPhone5 || iPhone4s) {
        self.clickNumberForEntreL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.clickNumberForEntreL.font = [UIFont systemFontOfSize:14.0f];
    }
    [self.contentView addSubview:_clickNumberForEntreL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
