//
//  SearchTutorTableViewCell.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/9.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "SearchTutorTableViewCell.h"

@interface SearchTutorTableViewCell ()
{
    UIImageView *bgImageView;
    UIImageView *hasSeenImageView;
    UILabel *aLine;
}

@end

@implementation SearchTutorTableViewCell

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
    
    self.searchIV = [[UIImageView alloc] initWithFrame:CGRectMake(20, 15, COMMONCELLHEIGHT - 70, COMMONCELLHEIGHT - 70)];
    //self.searchIV.backgroundColor = [UIColor yellowColor];
    _searchIV.image = [UIImage imageNamed:@"placeholders.png"];
    _searchIV.layer.masksToBounds = YES;
    _searchIV.layer.cornerRadius = 44;
    [self.contentView addSubview:_searchIV];
    
    self.searchNameL = [[UILabel alloc] initWithFrame:CGRectMake(_searchIV.frame.origin.x, _searchIV.frame.origin.y + _searchIV.frame.size.height, _searchIV.frame.size.width, 40)];
    //self.searchNameL.backgroundColor = [UIColor yellowColor];
    self.searchNameL.text = @"王雅蓉";
    self.searchNameL.textAlignment = NSTextAlignmentCenter;
    self.searchNameL.textColor = [UIColor colorWithRed:75 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0];
    self.searchNameL.font = [UIFont systemFontOfSize:18.0f];
    self.searchNameL.font = [UIFont boldSystemFontOfSize:20.0f];
    [self.contentView addSubview:_searchNameL];
    
    self.topicForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(_searchIV.frame.origin.x + _searchIV.frame.size.width + 10, _searchIV.frame.origin.y, WIDTH - 10 - _searchIV.frame.size.width - 40, 50)];
//    self.topicForSearchL.backgroundColor = [UIColor yellowColor];
    //self.topicForSearchL.font = [UIFont systemFontOfSize:15.0f];
    self.topicForSearchL.numberOfLines = 2;
    self.topicForSearchL.text = @"聊一聊风险投资是怎么一回事";
    [self.contentView addSubview:_topicForSearchL];
    
    //标签
    self.tagForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForSearchL.frame.origin.x, _topicForSearchL.frame.origin.y + _topicForSearchL.frame.size.height + 7, _topicForSearchL.frame.size.width, _topicForSearchL.frame.size.height - 25)];
    self.tagForSearchL.font = [UIFont systemFontOfSize:15.0f];
    self.tagForSearchL.numberOfLines = 2;
    self.tagForSearchL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_tagForSearchL];
    
    //学校
//    self.schoolForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(_topicForSearchL.frame.origin.x, _topicForSearchL.frame.origin.y + _topicForSearchL.frame.size.height , _topicForSearchL.frame.size.width - 30, _topicForSearchL.frame.size.height - 25)];
//    //self.schoolForApplyL.backgroundColor = [UIColor yellowColor];
//    self.schoolForSearchL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    self.schoolForSearchL.font = [UIFont systemFontOfSize:15.0f];
//    [self.contentView addSubview:_schoolForSearchL];
    
    //职位
//    self.positionForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(_schoolForSearchL.frame.origin.x, _schoolForSearchL.frame.origin.y + _schoolForSearchL.frame.size.height, _topicForSearchL.frame.size.width - _schoolForSearchL.frame.size.width - 5, _schoolForSearchL.frame.size.height)];
//    self.positionForSearchL.font = [UIFont systemFontOfSize:14.0f];
//    self.positionForSearchL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
//    [self.contentView addSubview:_positionForSearchL];
    
    aLine = [[UILabel alloc] initWithFrame:CGRectMake(_topicForSearchL.frame.origin.x, _tagForSearchL.frame.origin.y + _tagForSearchL.frame.size.height + 7, _topicForSearchL.frame.size.width - 5, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:235 / 255.0 green:236 / 255.0 blue:236 / 255.0 alpha:1.0];
    [self.contentView addSubview:aLine];
    
    hasSeenImageView = [[UIImageView alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, _searchNameL.frame.origin.y + 8, _searchNameL.frame.size.height - 20, _searchNameL.frame.size.height - 18)];
    hasSeenImageView.image = [UIImage imageNamed:@"hasSeenIcon.png"];
    [self.contentView addSubview:hasSeenImageView];
    
    self.hasSeenForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(hasSeenImageView.frame.origin.x + hasSeenImageView.frame.size.width + 5, _searchNameL.frame.origin.y, (_topicForSearchL.frame.size.width - 30) / 2.0, _searchNameL.frame.size.height)];
//    self.hasSeenForSearchL.backgroundColor = [UIColor yellowColor];
    //self.hasSeenForSearchL.text = @" 9人见过";
    self.hasSeenForSearchL.font = [UIFont systemFontOfSize:15.0f];
    self.hasSeenForSearchL.font = [UIFont boldSystemFontOfSize:14.5f];
    self.hasSeenForSearchL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    [self.contentView addSubview:_hasSeenForSearchL];
    
    self.clickNumberForSearchL = [[UILabel alloc] initWithFrame:CGRectMake(_hasSeenForSearchL.frame.origin.x + _hasSeenForSearchL.frame.size.width + 5, _hasSeenForSearchL.frame.origin.y, aLine.frame.size.width - _hasSeenForSearchL.frame.size.width - hasSeenImageView.frame.size.width - 10, _hasSeenForSearchL.frame.size.height)];
//    self.clickNumberForSearchL.backgroundColor = [UIColor yellowColor];
    //self.clickNumberForSearchL.text = @"1444/ 次";
    self.clickNumberForSearchL.textAlignment = NSTextAlignmentRight;
    self.clickNumberForSearchL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    self.clickNumberForSearchL.font = [UIFont boldSystemFontOfSize:14.5f];
    [self.contentView addSubview:_clickNumberForSearchL];
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
