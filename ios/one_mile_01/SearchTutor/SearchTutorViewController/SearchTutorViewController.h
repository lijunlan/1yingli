//
//  SearchTutorViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/9.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum
{
    SearchStateForLikenoUp = 0,    //按收藏+
    SearchStateForLikenoDown,
    SearchStateForCommentsUp,      //按评论+
    SearchStateForCommentsDown,
    SearchStateForPriceUp,          //按价格+
    SearchStateForPriceDown
    
}SearchState;

@interface SearchTutorViewController : UIViewController<UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *searchTV;
@property (nonatomic, copy) NSString *searchKey;
@property (nonatomic, strong)CAShapeLayer *indicator;
@property (nonatomic, strong)CAShapeLayer *commentIndicator;
@property (nonatomic, strong)CAShapeLayer *priceIndicator;
@property (nonatomic, strong) UITextField *searchTF;
@property (nonatomic, assign) SearchState stateForSearch;

@end
