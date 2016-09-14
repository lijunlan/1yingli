//
//  EditConnectionTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditConnectionTableViewCell.h"

@interface EditConnectionTableViewCell ()
{
    UILabel *aLine;
}

@end

@implementation EditConnectionTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    self.connectL = [[UILabel alloc] initWithFrame:CGRectMake(30, 10, 80, 40)];
    //self.connectL.backgroundColor = [UIColor yellowColor];
    self.connectL.font = [UIFont systemFontOfSize:14.0f];
    self.connectL.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    [self.contentView addSubview:_connectL];
    
    self.connectBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.connectBT.frame = CGRectMake(WIDTH - (_connectL.frame.origin.x - 5) * 2 - 50, _connectL.frame.origin.y, 70, 40);
    self.connectBT.titleLabel.font = [UIFont systemFontOfSize:14.0f];
    [self.connectBT setTitle:@"已绑定" forState:UIControlStateNormal];
    [self.connectBT setTitleColor:[UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0] forState:UIControlStateNormal];
    [self.contentView addSubview:_connectBT];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_connectL.frame.origin.x - 5, 60 - 2, WIDTH - (_connectL.frame.origin.x - 5) * 2, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
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
