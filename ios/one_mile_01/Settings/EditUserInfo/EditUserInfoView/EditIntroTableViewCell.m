//
//  EditIntroTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditIntroTableViewCell.h"

@interface EditIntroTableViewCell ()
{
    UILabel *introL;
    UILabel *aLine;
}

@end

@implementation EditIntroTableViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    introL = [[UILabel alloc] initWithFrame:CGRectMake(30, 10, 80, 40)];
    //introL.backgroundColor = [UIColor yellowColor];
    introL.text = @"个人简介";
    introL.font = [UIFont systemFontOfSize:14.0f];
    introL.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    [self.contentView addSubview:introL];
    
    self.introTV = [[UITextView alloc] initWithFrame:CGRectMake(introL.frame.origin.x, introL.frame.origin.y + introL.frame.size.height + 5, WIDTH - introL.frame.origin.x * 2, 100 - introL.frame.origin.y - introL.frame.size.height - 20)];
    self.introTV.textColor = [UIColor colorWithRed:129 / 255.0 green:129 / 255.0 blue:129 / 255.0 alpha:1.0];
    self.introTV.text = @"这个人很懒，什么也没有留下";
    self.introTV.scrollEnabled = YES;
    self.introTV.autoresizingMask = UIViewAutoresizingFlexibleHeight;
    self.introTV.font = [UIFont systemFontOfSize:14.0f];
    [self.contentView addSubview:_introTV];
    
    self.introTV.delegate = self;
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(introL.frame.origin.x - 5, 100 - 2, WIDTH - (introL.frame.origin.x - 5) * 2, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
}

-(void)textViewDidBeginEditing:(UITextView *)textView
{
    [self.myDelegate beginEdit];
}

-(void)textViewDidEndEditing:(UITextView *)textView
{
    [self.myDelegate endEdit:textView.text];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
