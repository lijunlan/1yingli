//
//  UIImage+UIImageByScaleToSize.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/10.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "UIImage+UIImageByScaleToSize.h"

@implementation UIImage (UIImageByScaleToSize)

-(UIImage *)imageByScalingToSize:(CGSize)targetSize
{
    UIImage *sourceImage = self;
    UIImage *newImage = nil;
    
    CGSize imageSize = sourceImage.size;
    
    //原尺寸
    CGFloat width = imageSize.width;
    CGFloat height = imageSize.height;
    
    //目标尺寸
    CGFloat targetWidth = targetSize.width;
    CGFloat targetHeight = targetSize.height;
    
    CGFloat scaleFactor = 0.0;   //收缩因素
    
    //伸缩尺寸
    CGFloat scaledWidth = targetWidth;
    CGFloat scaledHeight = targetHeight;
    
    CGPoint thumbnailPoint = CGPointMake(0.0,0.0);
    
    if (CGSizeEqualToSize(imageSize, targetSize) == NO) {
        
        //计算宽的收缩比例
        CGFloat widthFactor = targetWidth / width;
        //计算高的收缩比例
        CGFloat heightFactor = targetHeight / height;
        
        //按收缩比例小的来收缩图片
        if (widthFactor < heightFactor) {
            
            scaleFactor = widthFactor;
        } else {
            
            scaleFactor = heightFactor;
        }
        
        //将图片放大或者缩小
        scaledWidth  = width * scaleFactor;
        scaledHeight = height * scaleFactor;
        
        // center the image
        if (widthFactor < heightFactor) {
            
            thumbnailPoint.y = (targetHeight - scaledHeight) * 0.5;
        } else if (widthFactor > heightFactor) {
            
            thumbnailPoint.x = (targetWidth - scaledWidth) * 0.5;
        }
        
    }
    
    //this is actually the interesting part: redrawImage
    
    UIGraphicsBeginImageContext(targetSize);
    
    CGRect thumbnailRect = CGRectZero;
    
    thumbnailRect.origin = thumbnailPoint;
    thumbnailRect.size.width  = scaledWidth;
    thumbnailRect.size.height = scaledHeight;
    
    [sourceImage drawInRect:thumbnailRect];
    
    newImage = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext(); 
    
    if (newImage == nil) {
        
        NSLog(@"could not scale image");   
    }
    
    return newImage;
}

@end
