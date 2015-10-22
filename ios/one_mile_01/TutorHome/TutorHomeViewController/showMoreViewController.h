//
//  showMoreViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface showMoreViewController : UIViewController<UIWebViewDelegate>
@property (nonatomic, strong)UILabel *titleLabel;
@property (nonatomic, strong)UILabel *contentLabel;
@property (nonatomic, strong)UIButton *button;
@property (nonatomic, strong)UIScrollView *scrollV;
@property (nonatomic, strong)UIWebView *webV;


@end
