//
//  editUsersNormalTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/15.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "editUsersNormalTableViewCell.h"

@interface editUsersNormalTableViewCell ()<UITextFieldDelegate>
{
    UILabel *aLine;
}

@end

@implementation editUsersNormalTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    self.userNormL = [[UILabel alloc] initWithFrame:CGRectMake(30, 10, 80, 40)];
    //self.userNormL.backgroundColor = [UIColor yellowColor];
    self.userNormL.font = [UIFont systemFontOfSize:14.0f];
    self.userNormL.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    [self.contentView addSubview:_userNormL];
    
    self.userNormTF = [[UITextField alloc] initWithFrame:CGRectMake(_userNormL.frame.origin.x + _userNormL.frame.size.width + 10, 10, 140, 40)];
    self.userNormTF.font = [UIFont systemFontOfSize:14.0f];
    //self.userNormTF.backgroundColor = [UIColor yellowColor];
    [self.contentView addSubview:_userNormTF];
    
    self.userNormTF.delegate = self;
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_userNormL.frame.origin.x - 5, 60 - 2, WIDTH - (_userNormL.frame.origin.x - 5) * 2, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
}

#pragma mark -- textFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    [self.myDelegate beginEditText];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    [self.myDelegate endEditText:textField];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
