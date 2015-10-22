//
//  TobeTutorForServiceView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import "TobeTutorForServiceView.h"

#define TEXTCOLOR [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0]
#define TEXTHEIGHT 300
#define TAGBUTTON (WIDTH - 5 * 10) / 5.0

@interface TobeTutorForServiceView ()<UITextViewDelegate, UITextFieldDelegate>
{
    BOOL isPilot;
    BOOL isApply;
    BOOL isEntre;
    BOOL isCampus;
    BOOL isShare;
}

@property (nonatomic, strong) UIButton *pilotBT;
@property (nonatomic, strong) UIButton *applyBT;
@property (nonatomic, strong) UIButton *entreBT;
@property (nonatomic, strong) UIButton *campusBT;
@property (nonatomic, strong) UIButton *shareBT;

@end

@implementation TobeTutorForServiceView

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
       
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(5, 0, WIDTH - 10, 30)];
    titleL.text = @"  话题";
    titleL.font = [UIFont fontWithName:@"Helvetica-Bold" size:20.0f];
    titleL.textColor = TEXTCOLOR;
    [self addSubview:titleL];
    
    UILabel *subTitleL = [[UILabel alloc] initWithFrame:CGRectMake(5, titleL.frame.origin.y + titleL.frame.size.height, titleL.frame.size.width, 90)];
    subTitleL.font = [UIFont systemFontOfSize:15.0f];
    subTitleL.numberOfLines = 0;
    subTitleL.text = @"   丰富的话题说明，将有助于[一英里]通过您的申请。话题说明太过简单，通常会被拒绝。\n   您通过审核后，为了给您撰写介绍文案，还是必须请您回答下列问题，所以为了节省您的时间，请尽量详细回答，谢谢。";
    subTitleL.textColor = TEXTCOLOR;
    [self addSubview:subTitleL];
    
//    标题
    UILabel *topicTitleL = [[UILabel alloc] initWithFrame:CGRectMake(10, subTitleL.frame.origin.y + subTitleL.frame.size.height + 10, 80, 30)];
    topicTitleL.text = @"话题标题：";
    topicTitleL.textColor = TEXTCOLOR;
    topicTitleL.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:topicTitleL];
    
    UIView *topicTitleV = [[UIView alloc] initWithFrame:CGRectMake(topicTitleL.frame.origin.x + topicTitleL.frame.size.width, topicTitleL.frame.origin.y, WIDTH - 20 - 100, topicTitleL.frame.size.height)];
    topicTitleV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    topicTitleV.layer.borderWidth = 1.0f;
    [self addSubview:topicTitleV];
    
    self.topicTitleTF = [[UITextField alloc] initWithFrame:CGRectMake(10, 0, topicTitleV.frame.size.width - 10, topicTitleV.frame.size.height)];
    self.topicTitleTF.returnKeyType = UIReturnKeyDone;
    [topicTitleV addSubview:_topicTitleTF];
//    时长
    UILabel *topicDuringL = [[UILabel alloc] initWithFrame:CGRectMake(topicTitleL.frame.origin.x, topicTitleL.frame.origin.y + topicTitleL.frame.size.height + 5, topicTitleL.frame.size.width, topicTitleL.frame.size.height)];
    topicDuringL.text = @"时长：";
    topicDuringL.textColor = TEXTCOLOR;
    topicDuringL.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:topicDuringL];
    
    UIView *duringV = [[UIView alloc] initWithFrame:CGRectMake(topicTitleV.frame.origin.x, topicDuringL.frame.origin.y, topicTitleV.frame.size.width / 2.0 / 2.0, topicTitleV.frame.size.height)];
    duringV.layer.borderWidth = 1.0f;
    duringV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    [self addSubview:duringV];
    
    self.duringTF = [[UITextField alloc] initWithFrame:CGRectMake(_topicTitleTF.frame.origin.x, _topicTitleTF.frame.origin.y, duringV.frame.size.width - _topicTitleTF.frame.origin.x, _topicTitleTF.frame.size.height)];
    self.duringTF.returnKeyType = UIReturnKeyDone;
    [duringV addSubview:_duringTF];
    
    UILabel *duringAlert = [[UILabel alloc] initWithFrame:CGRectMake(duringV.frame.origin.x + duringV.frame.size.width, duringV.frame.origin.y + 5, WIDTH - 20 - topicDuringL.frame.size.width - duringV.frame.size.width, duringV.frame.size.height - 10)];
    duringAlert.text = @"（通常为1~1.5小时）";
    duringAlert.font = [UIFont systemFontOfSize:14.0f];
    duringAlert.textColor = TEXTCOLOR;
    [self addSubview:duringAlert];
//    价格
    UILabel *topicPriceL = [[UILabel alloc] initWithFrame:CGRectMake(topicDuringL.frame.origin.x, topicDuringL.frame.origin.y + topicDuringL.frame.size.height + 5, topicDuringL.frame.size.width, topicDuringL.frame.size.height)];
    topicPriceL.text = @"价格：";
    topicPriceL.textColor = TEXTCOLOR;
    topicPriceL.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:topicPriceL];
    
    UIView *priceV = [[UIView alloc] initWithFrame:CGRectMake(duringV.frame.origin.x, topicPriceL.frame.origin.y, duringV.frame.size.width, duringV.frame.size.height)];
    priceV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    priceV.layer.borderWidth = 1.0f;
    [self addSubview:priceV];
    
    self.priceTF = [[UITextField alloc] initWithFrame:CGRectMake(_duringTF.frame.origin.x, _duringTF.frame.origin.y, _duringTF.frame.size.width, _duringTF.frame.size.height)];
    self.priceTF.returnKeyType = UIReturnKeyDone;
    [priceV addSubview:_priceTF];
    
    UILabel *priceAlert = [[UILabel alloc] initWithFrame:CGRectMake(priceV.frame.origin.x + priceV.frame.size.width, priceV.frame.origin.y + 5, WIDTH - 20 - topicPriceL.frame.size.width - priceV.frame.size.width, priceV.frame.size.height - 10)];
    priceAlert.text = @"（建议最初的定价在300元以下）";
    priceAlert.textColor = TEXTCOLOR;
    priceAlert.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:priceAlert];
    
    _topicTitleTF.delegate = self;
    _duringTF.delegate = self;
    _priceTF.delegate = self;
    
//    话题内容
    UILabel *topicContentL = [[UILabel alloc] initWithFrame:CGRectMake(topicPriceL.frame.origin.x, topicPriceL.frame.origin.y + topicPriceL.frame.size.height + 10, WIDTH - topicPriceL.frame.origin.x * 2, topicPriceL.frame.size.height + 10)];
    topicContentL.textColor = TEXTCOLOR;
    topicContentL.font = [UIFont systemFontOfSize:16.0f];
    topicContentL.text = @"1.请输入话题主要内容（可以是话题摘要或概括的2-3个要点,1000字内）：";
    topicContentL.numberOfLines = 2;
    [self addSubview:topicContentL];
    
    self.topicContentTV = [[UITextView alloc] initWithFrame:CGRectMake(topicContentL.frame.origin.x, topicContentL.frame.origin.y + topicContentL.frame.size.height, WIDTH - topicContentL.frame.origin.x * 2, TEXTHEIGHT)];
    self.topicContentTV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    _topicContentTV.layer.borderWidth = 1.0f;
    _topicContentTV.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:_topicContentTV];
    
//    选择原因
    UILabel *reasonL = [[UILabel alloc] initWithFrame:CGRectMake(topicContentL.frame.origin.x, _topicContentTV.frame.origin.y + _topicContentTV.frame.size.height + 5, topicContentL.frame.size.width, topicContentL.frame.size.height - 10)];
    reasonL.textColor = TEXTCOLOR;
    reasonL.font = [UIFont systemFontOfSize:16.0f];
    reasonL.text = @"2.您选择这一话题的原因(500字内)：";
    reasonL.numberOfLines = 2;
    [self addSubview:reasonL];
    
    UIView *selectV = [[UIView alloc] initWithFrame:CGRectMake(0, reasonL.frame.origin.y + reasonL.frame.size.height, WIDTH, _topicContentTV.frame.size.height - 100)];
    [self addSubview:selectV];
    
    self.selectReasonTV = [[UITextView alloc] initWithFrame:CGRectMake(reasonL.frame.origin.x, 0, reasonL.frame.size.width, _topicContentTV.frame.size.height - 100)];
    _selectReasonTV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    _selectReasonTV.layer.borderWidth = 1.0f;
    _selectReasonTV.font = [UIFont systemFontOfSize:16.0f];
    [selectV addSubview:_selectReasonTV];
    
//    优势
    UILabel *advantageL = [[UILabel alloc] initWithFrame:CGRectMake(reasonL.frame.origin.x, selectV.frame.origin.y + selectV.frame.size.height + 5, reasonL.frame.size.width, topicContentL.frame.size.height)];
    advantageL.textColor = TEXTCOLOR;
    advantageL.font = [UIFont systemFontOfSize:16.0f];
    advantageL.text = @"3.您在这个话题上的优势（可以结合具体案例,1000字内）：";
    advantageL.numberOfLines = 2;
    [self addSubview:advantageL];
    
    self.advantageTV = [[UITextView alloc] initWithFrame:CGRectMake(advantageL.frame.origin.x, advantageL.frame.origin.y + advantageL.frame.size.height, advantageL.frame.size.width, _topicContentTV.frame.size.height)];
    _advantageTV.layer.borderWidth = 1.0f;
    _advantageTV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    _advantageTV.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:_advantageTV];
    
//    主题
    UILabel *subjectL = [[UILabel alloc] initWithFrame:CGRectMake(_advantageTV.frame.origin.x, _advantageTV.frame.origin.y + _advantageTV.frame.size.height + 5, advantageL.frame.size.width, advantageL.frame.size.height - 10)];
    subjectL.text = @"4.选择主题（选择您的主题）";
    subjectL.textColor = TEXTCOLOR;
    subjectL.font = [UIFont systemFontOfSize:16.0f];
    [self addSubview:subjectL];
    
    self.pilotBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.pilotBT.frame = CGRectMake(5, subjectL.frame.origin.y + subjectL.frame.size.height + 5, TAGBUTTON, TAGBUTTON - 15);
    [self.pilotBT setBackgroundImage:[UIImage imageNamed:@"niuniu_unselected.png"] forState:UIControlStateNormal];
    self.pilotBT.tag = 10110;
    [self addSubview:_pilotBT];
    
    [_pilotBT addTarget:self action:@selector(selectedSubjectAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *pilotL = [[UILabel alloc] initWithFrame:CGRectMake(0, _pilotBT.frame.origin.y + _pilotBT.frame.size.height, WIDTH / 5.0, 25)];
    pilotL.text = @"留学领航";
    pilotL.textColor = [UIColor darkGrayColor];
    pilotL.font = [UIFont systemFontOfSize:14.0f];
    pilotL.textAlignment = NSTextAlignmentCenter;
    [self addSubview:pilotL];
    
    self.applyBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.applyBT.frame = CGRectMake(_pilotBT.frame.origin.x + _pilotBT.frame.size.width + 10, _pilotBT.frame.origin.y, _pilotBT.frame.size.width, _pilotBT.frame.size.height);
    [self.applyBT setBackgroundImage:[UIImage imageNamed:@"copie_unselected.png"] forState:UIControlStateNormal];
    self.applyBT.tag = 10111;
    [self addSubview:_applyBT];
    
    [_applyBT addTarget:self action:@selector(selectedSubjectAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *applyL = [[UILabel alloc] initWithFrame:CGRectMake(pilotL.frame.origin.x + pilotL.frame.size.width, pilotL.frame.origin.y, pilotL.frame.size.width, pilotL.frame.size.height)];
    applyL.textAlignment = NSTextAlignmentCenter;
    applyL.font = [UIFont systemFontOfSize:14.0f];
    applyL.text = @"求职就业";
    applyL.textColor = [UIColor darkGrayColor];
    [self addSubview:applyL];
    
    self.entreBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.entreBT.frame = CGRectMake(_applyBT.frame.origin.x + _applyBT.frame.size.width + 10, _applyBT.frame.origin.y, _applyBT.frame.size.width, _applyBT.frame.size.height);
    [self.entreBT setBackgroundImage:[UIImage imageNamed:@"communication_unselected.png"] forState:UIControlStateNormal];
    self.entreBT.tag = 10112;
    [self addSubview:_entreBT];
    
    [_entreBT addTarget:self action:@selector(selectedSubjectAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *entreL = [[UILabel alloc] initWithFrame:CGRectMake(applyL.frame.origin.x + applyL.frame.size.width, applyL.frame.origin.y, applyL.frame.size.width, applyL.frame.size.height)];
    entreL.textColor = [UIColor darkGrayColor];
    entreL.textAlignment = NSTextAlignmentCenter;
    entreL.text = @"创业助力";
    entreL.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:entreL];
    
    self.campusBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.campusBT.frame = CGRectMake(_entreBT.frame.origin.x + _entreBT.frame.size.width + 10, _entreBT.frame.origin.y, _entreBT.frame.size.width, _entreBT.frame.size.height);
    [self.campusBT setBackgroundImage:[UIImage imageNamed:@"enlighten_unselected.png"] forState:UIControlStateNormal];
    self.campusBT.tag = 10113;
    [self addSubview:_campusBT];
    
    [_campusBT addTarget:self action:@selector(selectedSubjectAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *campusL = [[UILabel alloc] initWithFrame:CGRectMake(entreL.frame.origin.x + entreL.frame.size.width, entreL.frame.origin.y, entreL.frame.size.width, entreL.frame.size.height)];
    campusL.textColor = [UIColor darkGrayColor];
    campusL.textAlignment = NSTextAlignmentCenter;
    campusL.text = @"校园生活";
    campusL.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:campusL];
    
    self.shareBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.shareBT.frame = CGRectMake(_campusBT.frame.origin.x + _campusBT.frame.size.width + 10, _campusBT.frame.origin.y, _campusBT.frame.size.width, _campusBT.frame.size.height);
    [self.shareBT setBackgroundImage:[UIImage imageNamed:@"movie_unselected.png"] forState:UIControlStateNormal];
    self.shareBT.tag = 10114;
    [self addSubview:_shareBT];
    
    [_shareBT addTarget:self action:@selector(selectedSubjectAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *shareL = [[UILabel alloc] initWithFrame:CGRectMake(campusL.frame.origin.x + campusL.frame.size.width, campusL.frame.origin.y, campusL.frame.size.width, campusL.frame.size.height)];
    shareL.text = @"猎奇分享";
    shareL.textAlignment = NSTextAlignmentCenter;
    shareL.textColor = [UIColor darkGrayColor];
    shareL.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:shareL];
    
//    定义键盘上的 完成 按钮
    UIToolbar *topViewForKeyboard = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, WIDTH, 35)];
    [topViewForKeyboard setBarStyle:UIBarStyleDefault];
    
    //定义两个flexibleSpace的button，放在toolBar上，这样完成按钮就会在最右边
    UIBarButtonItem *button1 = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
    UIBarButtonItem *button2 = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
    UIBarButtonItem *doneButton = [[UIBarButtonItem alloc] initWithTitle:@"完成" style:UIBarButtonItemStyleDone target:self action:@selector(resignKeyboardAction:)];
    
    NSArray *buttonsArray = [NSArray arrayWithObjects:button1, button2, doneButton, nil];
    [topViewForKeyboard setItems:buttonsArray];
    
    [_topicContentTV setInputAccessoryView:topViewForKeyboard];
    [_selectReasonTV setInputAccessoryView:topViewForKeyboard];
    [_advantageTV setInputAccessoryView:topViewForKeyboard];
}

//主题选择 事件
-(void)selectedSubjectAction:(UIButton *)button
{
    if (button.tag == 10110) {
        
        isPilot = !isPilot;
        
        if (!isPilot) {
            [button setBackgroundImage:[UIImage imageNamed:@"niuniu_selected.png"] forState:UIControlStateNormal];
        } else {
            [button setBackgroundImage:[UIImage imageNamed:@"niuniu_unselected.png"] forState:UIControlStateNormal];
        }
    } else if (button.tag == 10111) {
        
        isApply = !isApply;
        
        if (!isApply) {
            [button setBackgroundImage:[UIImage imageNamed:@"copie_unselected.png"] forState:UIControlStateNormal];
        } else {
            [button setBackgroundImage:[UIImage imageNamed:@"copie_selected.png"] forState:UIControlStateNormal];
        }
    } else if (button.tag == 10112) {
        
        isEntre = !isEntre;
        
        if (!isEntre) {
            [button setBackgroundImage:[UIImage imageNamed:@"communication_unselected.png"] forState:UIControlStateNormal];
        } else {
            [button setBackgroundImage:[UIImage imageNamed:@"communication_selected.png"] forState:UIControlStateNormal];
        }
    } else if (button.tag == 10113) {
        
        isCampus = !isCampus;
        
        if (!isCampus) {
            [button setBackgroundImage:[UIImage imageNamed:@"enlighten_unselected.png"] forState:UIControlStateNormal];
        } else {
            [button setBackgroundImage:[UIImage imageNamed:@"enlighten_selected.png"] forState:UIControlStateNormal];
        }
    } else if (button.tag == 10114) {
        
        isShare = !isShare;
        
        if (!isShare) {
            [button setBackgroundImage:[UIImage imageNamed:@"movie_unselected.png"] forState:UIControlStateNormal];
        } else {
            [button setBackgroundImage:[UIImage imageNamed:@"movie_selected.png"] forState:UIControlStateNormal];
        }
    }
}

//键盘撤销
-(void)resignKeyboardAction:(UIBarButtonItem *)bar
{
    [_topicContentTV resignFirstResponder];
    [_selectReasonTV resignFirstResponder];
    [_advantageTV resignFirstResponder];
}

#pragma mark -- textfieldDelegate
-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return NO;
}

-(NSString *)saveServiceInfo
{
    NSString *infoStr = [NSString stringWithFormat:@"{\"title\":\"%@\",\"time\":\"%@\",\"price\":\"%@\",\"content\":\"%@\",\"reason\":\"%@\",\"advantage\":\"%@\",\"tips\":[", _topicTitleTF.text, _duringTF.text, _priceTF.text, _topicContentTV.text, _selectReasonTV.text, _advantageTV.text];
    
    NSMutableArray *array = [NSMutableArray array];
    
    if (isPilot) [array addObject:@"1"]; else [array addObject:@"0"];
    if (isApply) [array addObject:@"2"]; else [array addObject:@"0"];
    if (isEntre) [array addObject:@"4"]; else [array addObject:@"0"];
    if (isCampus) [array addObject:@"8"]; else [array addObject:@"0"];
    if (isShare) [array addObject:@"16"]; else [array addObject:@"0"];
    
    for (NSString *tmpStr in array) {
        
        if ([tmpStr isEqualToString:@"1"]) {
            infoStr = [infoStr stringByAppendingFormat:@"{\"id\":\"%@\"},", tmpStr];
        }
        if ([tmpStr isEqualToString:@"2"]) {
            infoStr = [infoStr stringByAppendingFormat:@"{\"id\":\"%@\"},", tmpStr];
        }
        if ([tmpStr isEqualToString:@"4"]) {
            infoStr = [infoStr stringByAppendingFormat:@"{\"id\":\"%@\"},", tmpStr];
        }
        if ([tmpStr isEqualToString:@"8"]) {
            infoStr = [infoStr stringByAppendingFormat:@"{\"id\":\"%@\"},", tmpStr];
        }
        if ([tmpStr isEqualToString:@"16"]) {
            infoStr = [infoStr stringByAppendingFormat:@"{\"id\":\"%@\"},", tmpStr];
        }
    }
    
    infoStr = [infoStr substringWithRange:NSMakeRange(0, infoStr.length - 1)];
    
    return [infoStr stringByAppendingFormat:@"]}"];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
