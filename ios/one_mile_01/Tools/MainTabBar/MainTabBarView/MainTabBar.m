//
//  MainTabBar.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/31.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "MainTabBar.h"

@implementation MainTabBar

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        //self.translucent = NO;
    }
    
    return self;
}

-(void)layoutSubviews
{
    [super layoutSubviews];
    
    CGFloat tabbarWidth = self.frame.size.width / 5.0;
    NSInteger index = 0;
    
    for (UIView *child in self.subviews) {
        
        Class class = NSClassFromString(@"UITabBarButton");
        if ([child.class isSubclassOfClass:class]) {
            
            CGRect rect = child.frame;
            rect.origin.x = self.frame.size.width / 5.0 *index;
            rect.size.width = tabbarWidth;
            child.frame = rect;
            
            CGPoint point = child.center;
            point.y = self.frame.size.height / 2.0;
            if (index == 3) {
                point.x += 5;
            }
            child.center = point;
            
            index ++;
        }
    }
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
