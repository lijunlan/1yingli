//
//  RootTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "RootTableViewCell.h"

@implementation RootTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    self.mainLabel = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 62.5, 250 / 2.0 - 30, 125, 30)];
    self.mainLabel.textColor = [UIColor whiteColor];
    self.mainLabel.textAlignment = NSTextAlignmentCenter;
    self.mainLabel.font = [UIFont boldSystemFontOfSize:30.0f];
    [self.contentView addSubview:_mainLabel];
    
    self.subLabel = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 90, _mainLabel.frame.origin.y + _mainLabel.frame.size.height + 5, 180, 17)];
    self.subLabel.textColor = [UIColor whiteColor];
    self.subLabel.textAlignment = NSTextAlignmentCenter;
    self.subLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    [self.contentView addSubview:_subLabel];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
