//
//  yourTutorsTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "yourTutorsTableViewCell.h"

@interface yourTutorsTableViewCell ()
{
    UIImageView *bgImageView;
    UILabel *aLine;
}

@end

@implementation yourTutorsTableViewCell

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
    
    self.youTutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.tutorIV.backgroundColor = [UIColor yellowColor];
    //self.tutorIV.image = [UIImage imageNamed:@"placeholders.png"];
    _youTutorIV.layer.masksToBounds = YES;
    _youTutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_youTutorIV];
    
    self.youTutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_youTutorIV.frame.origin.x, _youTutorIV.frame.origin.y + _youTutorIV.frame.size.height, _youTutorIV.frame.size.width, 40)];
    self.youTutorNameL.text = @"王雅蓉";
    self.youTutorNameL.textAlignment = NSTextAlignmentCenter;
    self.youTutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.youTutorNameL.font = [UIFont systemFontOfSize:18.0f];
    self.youTutorNameL.font = [UIFont boldSystemFontOfSize:20.0f];
    [self.contentView addSubview:_youTutorNameL];
    
    //话题
    self.topicForyouTutorL = [[UILabel alloc] initWithFrame:CGRectMake(_youTutorIV.frame.origin.x + _youTutorIV.frame.size.width + 10, _youTutorIV.frame.origin.y + 5, WIDTH - 10 - _youTutorIV.frame.size.width - 40, 50)];
    //self.topicL.backgroundColor = [UIColor yellowColor];
    self.topicForyouTutorL.numberOfLines = 2;
    self.topicForyouTutorL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicForyouTutorL];
    
    self.priceForyouTutorL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForyouTutorL.frame.origin.x, _topicForyouTutorL.frame.origin.y + _topicForyouTutorL.frame.size.height + 7, _topicForyouTutorL.frame.size.width - 30, _topicForyouTutorL.frame.size.height - 25)];
    self.priceForyouTutorL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.priceForyouTutorL.font = [UIFont systemFontOfSize:15.0f];
    //self.schoolL.backgroundColor = [UIColor yellowColor];
    [self.contentView addSubview:_priceForyouTutorL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_priceForyouTutorL.frame.origin.x - 5, _priceForyouTutorL.frame.origin.y + _priceForyouTutorL.frame.size.height + 7, _topicForyouTutorL.frame.size.width, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    self.stateForyouTutorL = [[UILabel alloc] initWithFrame:CGRectMake(aLine.frame.origin.x + 5, _youTutorNameL.frame.origin.y, _topicForyouTutorL.frame.size.width, _youTutorNameL.frame.size.height)];
    self.stateForyouTutorL.textColor = [UIColor colorWithRed:155 / 255.0 green:156 / 255.0 blue:157 / 255.0 alpha:1.0];
    self.stateForyouTutorL.font = [UIFont systemFontOfSize:12.0f];
    self.stateForyouTutorL.font = [UIFont boldSystemFontOfSize:12.0f];
    [self.contentView addSubview:_stateForyouTutorL];
}

@end
