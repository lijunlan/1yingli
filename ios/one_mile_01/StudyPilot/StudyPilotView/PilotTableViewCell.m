//
//  PilotTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "PilotTableViewCell.h"

@interface PilotTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation PilotTableViewCell

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
    
    //话题
    self.topicL = [[UILabel alloc] initWithFrame:CGRectMake(_tutorIV.frame.origin.x + _tutorIV.frame.size.width + 10, _tutorIV.frame.origin.y + 5, WIDTH - 10 - _tutorIV.frame.size.width - 40, 50)];
    //self.topicL.backgroundColor = [UIColor yellowColor];
    //self.topicL.font = [UIFont systemFontOfSize:16.0f];
    self.topicL.numberOfLines = 2;
    self.topicL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicL];
    
    //专业 学校
    self.simInfoL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _topicL.frame.origin.y + _topicL.frame.size.height + 2, _topicL.frame.size.width, _topicL.frame.size.height - 25)];
    self.simInfoL.font = [UIFont systemFontOfSize:15.0f];
    self.simInfoL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_simInfoL];
    
//    self.schoolL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _topicL.frame.origin.y + _topicL.frame.size.height, _topicL.frame.size.width - 30, _topicL.frame.size.height - 25)];
//    self.schoolL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.schoolL.font = [UIFont systemFontOfSize:15.0f];
//    //self.schoolL.backgroundColor = [UIColor yellowColor];
//    self.schoolL.textAlignment = NSTextAlignmentCenter;
//    [self.contentView addSubview:_schoolL];
//    
//    self.positionL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolL.frame.origin.x, _schoolL.frame.origin.y + _schoolL.frame.size.height, _schoolL.frame.size.width, _schoolL.frame.size.height)];
//    //self.positionL.backgroundColor = [UIColor yellowColor];
//    //空格，锐角化的原因
//    self.positionL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.positionL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionL.textAlignment = NSTextAlignmentCenter;
////  宽度和高度的计算NSString,字体比原字体大小大一码，这样能够全部显示
////    CGSize detailSize = [_positionL.text sizeWithFont:[UIFont systemFontOfSize:14.0f] constrainedToSize:CGSizeMake(200, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
////    detailSize.height = _positionL.frame.size.height;
////    self.positionL.frame = CGRectMake(_positionL.frame.origin.x, _positionL.frame.origin.y, detailSize.width, detailSize.height);
//    [self.contentView addSubview:_positionL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_simInfoL.frame.origin.x - 5, _simInfoL.frame.origin.y + _simInfoL.frame.size.height + 7, _topicL.frame.size.width, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _tutorNameL.frame.origin.y + 8, _tutorNameL.frame.size.height - 20, _tutorNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, _tutorNameL.frame.origin.y, (_topicL.frame.size.width - 30) / 2.0 - 10, _tutorNameL.frame.size.height)];
    //self.hasSeenL.backgroundColor = [UIColor yellowColor];
    self.hasSeenL.text = @" 9人见过";
    if (iPhone5 || iPhone4s) {
        self.hasSeenL.font = [UIFont systemFontOfSize:12.0f];
        self.hasSeenL.font = [UIFont boldSystemFontOfSize:11.5f];
    } else {
        self.hasSeenL.font = [UIFont systemFontOfSize:14.0f];
        self.hasSeenL.font = [UIFont boldSystemFontOfSize:13.5f];
    }
    self.hasSeenL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenL];
    
    self.clickNumberL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenL.frame.origin.x + _hasSeenL.frame.size.width, _hasSeenL.frame.origin.y, aLine.frame.size.width - _hasSeenL.frame.size.width - hasSeenImageView.frame.size.width, _hasSeenL.frame.size.height)];
    //self.clickNumberL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberL.text = @"1444/ 次";
    self.clickNumberL.textAlignment = NSTextAlignmentRight;
    self.clickNumberL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    if (iPhone5 || iPhone4s) {
        self.clickNumberL.font = [UIFont systemFontOfSize:12.0f];
    } else {
        self.clickNumberL.font = [UIFont systemFontOfSize:14.0f];
    }
    [self.contentView addSubview:_clickNumberL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
