//
//  QuerryInfoTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "QuerryInfoTableViewCell.h"

#define THREEWORDS 60  //三个字的宽度

@interface QuerryInfoTableViewCell ()
{
    UIImageView *bgImageView;
    UILabel *aLine;
}

@end

@implementation QuerryInfoTableViewCell

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
    self.topicL.numberOfLines = 2;
    //self.topicL.backgroundColor = [UIColor yellowColor];
    self.topicL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicL];
    
    /*
    self.selectTimeL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _topicL.frame.origin.y + _topicL.frame.size.height + 5, _topicL.frame.size.width - 30, _topicL.frame.size.height - 15)];
    self.selectTimeL.backgroundColor = [UIColor yellowColor];
    //空格，锐角化的原因
    //self.selectTimeL.text;
    self.selectTimeL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.selectTimeL.font = [UIFont systemFontOfSize:15.0f];
    //self.positionL.layer.masksToBounds = YES;
    //self.positionL.layer.cornerRadius = 17.0;
    //self.positionL.layer.borderWidth = 1.0f;
    //self.positionL.layer.borderColor = [[UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0] CGColor];
    [self.contentView addSubview:_selectTimeL];
    */
    
    self.moneyL = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x + 5, _topicL.frame.origin.y + _topicL.frame.size.height + 7, _topicL.frame.size.width - 5, 30)];
    self.moneyL.font = [UIFont systemFontOfSize:14.0f];
    self.moneyL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_moneyL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicL.frame.origin.x, _moneyL.frame.origin.y + _moneyL.frame.size.height + 5, _topicL.frame.size.width - 10, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
     
    self.alertStateL = [[UILabel alloc] initWithFrame:CGRectMake(aLine.frame.origin.x + 3, _tutorNameL.frame.origin.y, aLine.frame.size.width, _tutorNameL.frame.size.height)];
    //_alertStateL1.backgroundColor = [UIColor blueColor];
    _alertStateL.text = @"";
    _alertStateL.font = [UIFont systemFontOfSize:12.0f];
    _alertStateL.numberOfLines = 2;
    _alertStateL.textColor = [UIColor colorWithRed:143 / 255.0 green:143 / 255.0 blue:143 / 255.0 alpha:1.0];
    [self.contentView addSubview:_alertStateL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
