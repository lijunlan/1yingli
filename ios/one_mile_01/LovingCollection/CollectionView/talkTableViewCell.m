//
//  talkTableViewCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/25.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "talkTableViewCell.h"

@interface talkTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation talkTableViewCell

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
    
    self.tutorIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.tutorIV.backgroundColor = [UIColor yellowColor];
    //self.tutorIV.image = [UIImage imageNamed:@"placeholders.png"];
    _tutorIV.layer.masksToBounds = YES;
    _tutorIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_tutorIV];
    
    self.tutorNameL = [[UILabel alloc] initWithFrame:CGRectMake(_tutorIV.frame.origin.x, _tutorIV.frame.origin.y + _tutorIV.frame.size.height, _tutorIV.frame.size.width, 40)];
    //self.tutorNameL.backgroundColor = [UIColor yellowColor];
    self.tutorNameL.text = @"王雅蓉";
    self.tutorNameL.textAlignment = NSTextAlignmentCenter;
    self.tutorNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.tutorNameL.font = [UIFont systemFontOfSize:18.0f];
    self.tutorNameL.font = [UIFont boldSystemFontOfSize:20.0f];
    [self.contentView addSubview:_tutorNameL];
    
    self.topicL = [[UILabel alloc] initWithFrame:CGRectMake(_tutorIV.frame.origin.x + _tutorIV.frame.size.width + 10, _tutorIV.frame.origin.y, WIDTH - 10 - _tutorIV.frame.size.width - 40, 50)];
    //self.topicL.backgroundColor = [UIColor yellowColor];
    self.topicL.numberOfLines = 2;
    [self.contentView addSubview:_topicL];
    
    self.simInfoForCollectionL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _topicL.frame.origin.y + _topicL.frame.size.height + 7, _topicL.frame.size.width, _topicL.frame.size.height - 25)];
    self.simInfoForCollectionL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.simInfoForCollectionL.font = [UIFont systemFontOfSize:15.0f];
    [self.contentView addSubview:_simInfoForCollectionL];
//    self.schoolL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _topicL.frame.origin.y + _topicL.frame.size.height + 10, _topicL.frame.size.width - 30, _topicL.frame.size.height - 25)];
//    //self.schoolL.backgroundColor = [UIColor yellowColor];
//    self.schoolL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.schoolL.font = [UIFont systemFontOfSize:15.0f];
//    //self.schoolL.textAlignment = NSTextAlignmentCenter;
//    [self.contentView addSubview:_schoolL];
//    
//    self.positionL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolL.frame.origin.x, _schoolL.frame.origin.y + _schoolL.frame.size.height, _schoolL.frame.size.width, _schoolL.frame.size.height)];
//    //self.positionL.backgroundColor = [UIColor yellowColor];
//    self.positionL.text = @" AppleSquare CEO";
//    self.positionL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.positionL.font = [UIFont systemFontOfSize:14.0f];
//    //self.positionL.textAlignment = NSTextAlignmentCenter;
//    [self.contentView addSubview:_positionL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _simInfoForCollectionL.frame.origin.y + _simInfoForCollectionL.frame.size.height + 7, _topicL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    /*
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _tutorNameL.frame.origin.y + 10, _tutorNameL.frame.size.height - 20, _tutorNameL.frame.size.height - 20)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
     */
    
    self.tutorPriceL = [[UILabel alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _tutorNameL.frame.origin.y, _topicL.frame.size.width / 2.0, _tutorNameL.frame.size.height)];
    //self.tutorPriceL.backgroundColor = [UIColor yellowColor];
    self.tutorPriceL.text = @"";
    self.tutorPriceL.font = [UIFont systemFontOfSize:14.0f];
    self.tutorPriceL.font = [UIFont boldSystemFontOfSize:13.5f];
    self.tutorPriceL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_tutorPriceL];
}

@end
