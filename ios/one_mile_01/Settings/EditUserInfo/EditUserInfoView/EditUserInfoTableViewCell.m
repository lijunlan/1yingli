//
//  EditUserInfoTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditUserInfoTableViewCell.h"

@interface EditUserInfoTableViewCell ()<UITextFieldDelegate>
{
    UILabel *aLine;
}

@end

@implementation EditUserInfoTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    self.userInfoL = [[UILabel alloc] initWithFrame:CGRectMake(30, 10, 80, 40)];
    //self.userInfoL.backgroundColor = [UIColor yellowColor];
    self.userInfoL.font = [UIFont systemFontOfSize:14.0f];
    self.userInfoL.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    [self.contentView addSubview:_userInfoL];
    
    self.userInfoTF = [[UITextField alloc] initWithFrame:CGRectMake(_userInfoL.frame.origin.x + _userInfoL.frame.size.width + 10, 10, 140, 40)];
    self.userInfoTF.userInteractionEnabled = NO;
    self.userInfoTF.font = [UIFont systemFontOfSize:14.0f];
    //self.userInfoTF.backgroundColor = [UIColor yellowColor];
    [self.contentView addSubview:_userInfoTF];
    
    self.userInfoTF.delegate = self;
    
    self.telOrEmBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.telOrEmBT.frame = CGRectMake(WIDTH - 30 - 20, 10, 30, 30);
    [self.telOrEmBT setBackgroundImage:[UIImage imageNamed:@"editIcon.png"] forState:UIControlStateNormal];
    [self.contentView addSubview:_telOrEmBT];
    
    [self.telOrEmBT addTarget:self action:@selector(editTelOrEmButton:) forControlEvents:UIControlEventTouchUpInside];
        
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_userInfoL.frame.origin.x - 5, 60 - 2, WIDTH - (_userInfoL.frame.origin.x - 5) * 2, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
}

-(void)editTelOrEmButton:(UIButton *)button
{
    if (button.tag == 10032) {
        
        [self.myDelegate changeTelNum];
    } else if (button.tag == 10033) {
    
        [self.myDelegate changeEmail];
    }
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
