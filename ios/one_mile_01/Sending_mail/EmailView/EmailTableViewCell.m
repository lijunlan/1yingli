//
//  EmailTableViewCell.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EmailTableViewCell.h"

@implementation EmailTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    self.dateL = [[UILabel alloc] initWithFrame:CGRectMake(10, 10, WIDTH - 20, 20)];
    self.dateL.font = [UIFont systemFontOfSize:14.0f];
    self.dateL.textColor = [UIColor colorWithRed:83 / 255.0 green:86 / 255.0 blue:89 / 255.0 alpha:1.0];
    [self.contentView addSubview:_dateL];
    
    self.messageL = [[UILabel alloc] initWithFrame:CGRectMake(_dateL.frame.origin.x, _dateL.frame.origin.y + _dateL.frame.size.height + 5, _dateL.frame.size.width, _dateL.frame.size.height)];
    self.messageL.font = [UIFont systemFontOfSize:15.0f];
    self.messageL.textColor = [UIColor colorWithRed:83 / 255.0 green:86 / 255.0 blue:89 / 255.0 alpha:1.0];
    [self.contentView addSubview:_messageL];
    
    UILabel *aLine = [[UILabel alloc] initWithFrame:CGRectMake(_dateL.frame.origin.x, _messageL.frame.origin.y + _messageL.frame.size.height + 5, _messageL.frame.size.width, 1)];
    aLine.backgroundColor = [UIColor lightGrayColor];
    [self.contentView addSubview:aLine];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
