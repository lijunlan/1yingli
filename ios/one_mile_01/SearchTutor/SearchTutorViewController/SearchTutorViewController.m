//
//  SearchTutorViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/9.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "SearchTutorViewController.h"
#import "SearchTutorTableViewCell.h"
#import "SearchTutorModel.h"
#import "tutorHomeViewController.h"

@interface SearchTutorViewController ()<UIAlertViewDelegate, UITextFieldDelegate>

@property (nonatomic, strong) NSMutableArray *searchArray;
@property (nonatomic, assign) BOOL downUpdata;
@property (nonatomic, assign) NSInteger indexPage;
@property (nonatomic, assign) NSInteger rangleTag;  //为1时，表示除了第一个，其他的三角都与他相反
@property (nonatomic, assign) BOOL isRurn;
@property (nonatomic, assign) BOOL isRurnComment;
@property (nonatomic, assign) BOOL isRurnFavorite;

@end

@implementation SearchTutorViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.searchArray = [NSMutableArray array];
        self.indexPage = 1;
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor colorWithRed:34 / 255.0 green:46 / 255.0 blue:72 / 255.0 alpha:1.0];
    
    UIButton *pushBT = [UIButton buttonWithType:UIButtonTypeCustom];
    pushBT.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    //[pushBT setBackgroundColor:[UIColor blackColor]];
    [pushBT setBackgroundImage:[UIImage imageNamed:@"search_popBack.png"] forState:UIControlStateNormal];
    [pushBT addTarget:self action:@selector(popToRoot:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:pushBT];
    
    UILabel *pilotTitleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 40, pushBT.frame.origin.y, 80, pushBT.frame.size.height)];
    pilotTitleL.text = @"搜索导师";
    pilotTitleL.textColor = [UIColor whiteColor];
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    [self createSubviews];
    
    self.downUpdata = YES;
    //[self getSearchData];
    
    //刷新
    [self.searchTV addHeaderWithTarget:self action:@selector(headerRefesh)];
    [self.searchTV headerBeginRefreshing];
    
    [self.searchTV addFooterWithTarget:self action:@selector(footerRefesh)];
}

-(void)popToRoot:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

-(void)createSubviews
{
    UIView *navigationV = [[UIView alloc] initWithFrame:CGRectMake(20 + PUSHANDPOPBUTTONSIZE, 0, WIDTH - PUSHANDPOPBUTTONSIZE - 20, NAVIGATIONHEIGHT)];
    navigationV.backgroundColor = [UIColor clearColor];
    [self.view addSubview:navigationV];
    
    UIView *searchView = [[UIView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT + 10, WIDTH, 40)];
    searchView.backgroundColor = [UIColor colorWithRed:45 / 255.0 green:59 / 255.0 blue:88 / 255.0 alpha:1.0];
    [self.view addSubview:searchView];
    
    UIImageView *searchIV = [[UIImageView alloc] initWithFrame:CGRectMake(10, 5, 25, 30)];
    searchIV.image = [UIImage imageNamed:@"search_icon.png"];
    [searchView addSubview:searchIV];
    
    self.searchTF = [[UITextField alloc] initWithFrame:CGRectMake(searchIV.frame.origin.x + searchIV.frame.size.width + 10, 0, WIDTH - searchIV.frame.size.width - 20, 40)];
    self.searchTF.backgroundColor = [UIColor clearColor];
    self.searchTF.textColor = [UIColor colorWithRed:126 / 255.0 green:135 / 255.0 blue:158 / 255.0 alpha:1.0];
    self.searchTF.font = [UIFont systemFontOfSize:14.0f];
    self.searchTF.text = self.searchKey;
    [searchView addSubview:_searchTF];
    
    self.searchTF.textColor = [UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0];
    self.searchTF.delegate = self;
    self.searchTF.text = self.searchKey;  //@"搜索：学校/专业/导师";
    self.searchTF.clearsOnBeginEditing = YES;
    _searchTF.returnKeyType = UIReturnKeyDone;
    _searchTF.clearButtonMode = UITextFieldViewModeAlways;
    
    self.searchTF.leftView.frame = CGRectMake(10, 0, 25, 30);
    
    
    //按收藏 WIDTH / 4 / 6(x)
    UIView *favoriteV = [[UIView alloc] initWithFrame:CGRectMake((WIDTH / 2 - WIDTH / 4) / 2, searchView.frame.origin.y + searchView.frame.size.height + 15, WIDTH / 4, 30)];
    favoriteV.backgroundColor = [UIColor colorWithRed:45 / 255.0 green:59 / 255.0 blue:88 / 255.0 alpha:1.0];
    [self.view addSubview:favoriteV];
    
    UIImageView *favoriteIV = [[UIImageView alloc] initWithFrame:CGRectMake(5, 5, 20, favoriteV.frame.size.height - 10)];
    favoriteIV.image = [UIImage imageNamed:@"search_favorite.png"];
    [favoriteV addSubview:favoriteIV];
    
    UILabel *favoriteL = [[UILabel alloc] initWithFrame:CGRectMake(favoriteIV.frame.origin.x + favoriteIV.frame.size.width, favoriteIV.frame.origin.y, favoriteV.frame.size.width - favoriteIV.frame.origin.x - favoriteIV.frame.size.width, favoriteIV.frame.size.height)];
    favoriteL.text = @"  按收藏";
    favoriteL.textColor = [UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0];
    if (iPhone4s || iPhone5) {
        favoriteL.font = [UIFont systemFontOfSize:10.0f];
    }else{
        favoriteL.font = [UIFont systemFontOfSize:13.0f];
    }
    [favoriteV addSubview:favoriteL];
//    三角形
    UIView *equilateralV = [[UIView alloc]init];
    self.rangleTag = 1;
    self.indicator = [self createIndicatorWithColor:[UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0] andPosition:CGPointMake(favoriteL.frame.origin.x + favoriteL.frame.size.width - 10, 17)];
    [equilateralV.layer addSublayer:self.indicator];
    [favoriteV addSubview:equilateralV];
    UITapGestureRecognizer *equilaterlTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(equilateralTapAction)];
    [favoriteV addGestureRecognizer:equilaterlTap];
    
    //按评价
//    UIView *commentV = [[UIView alloc] initWithFrame:CGRectMake(favoriteV.frame.origin.x + favoriteV.frame.size.width + WIDTH / 4 / 6 * 2, favoriteV.frame.origin.y, favoriteV.frame.size.width, favoriteV.frame.size.height)];
//    commentV.backgroundColor = [UIColor colorWithRed:45 / 255.0 green:59 / 255.0 blue:88 / 255.0 alpha:1.0];
//    [self.view addSubview:commentV];
//    
//    UIImageView *commentIV = [[UIImageView alloc] initWithFrame:CGRectMake(favoriteIV.frame.origin.x, favoriteIV.frame.origin.y, favoriteIV.frame.size.width, favoriteIV.frame.size.height)];
//    commentIV.image = [UIImage imageNamed:@"search_comment.png"];
//    [commentV addSubview:commentIV];
//    
//    UILabel *commentL = [[UILabel alloc] initWithFrame:CGRectMake(favoriteL.frame.origin.x, favoriteL.frame.origin.y, favoriteL.frame.size.width, favoriteL.frame.size.height)];
//    commentL.text = @"  按评价";
//    commentL.textColor = [UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0];
//    commentL.userInteractionEnabled = YES;
//    if (iPhone4s || iPhone5) {
//        commentL.font = [UIFont systemFontOfSize:10.0f];
//    }else{
//        commentL.font = [UIFont systemFontOfSize:13.0f];
//    }
//    [commentV addSubview:commentL];
//    
//    //    三角形
//    UIView *commentEquilateralV = [[UIView alloc]init];
//    self.commentIndicator = [self createIndicatorWithColor:[UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0] andPosition:CGPointMake(commentL.frame.size.width + commentL.frame.origin.x - 10, 17)];
//    [commentEquilateralV.layer addSublayer:self.commentIndicator];
//    [commentV addSubview:commentEquilateralV];
//    
//    UITapGestureRecognizer *commentT = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(commentTAction)];
//    [commentL addGestureRecognizer:commentT];
    
    //按价格
    UIView *priceV = [[UIView alloc] initWithFrame:CGRectMake(favoriteV.frame.origin.x + favoriteV.frame.size.width + favoriteV.frame.origin.x * 2, favoriteV.frame.origin.y, favoriteV.frame.size.width, favoriteV.frame.size.height)];
    priceV.backgroundColor = [UIColor colorWithRed:45 / 255.0 green:59 / 255.0 blue:88 / 255.0 alpha:1.0];
    [self.view addSubview:priceV];
    
    UIImageView *priceIV = [[UIImageView alloc] initWithFrame:CGRectMake(favoriteIV.frame.origin.x, favoriteIV.frame.origin.y, favoriteIV.frame.size.width, favoriteIV.frame.size.height)];
    priceIV.image = [UIImage imageNamed:@"search_buy.png"];
    [priceV addSubview:priceIV];
    
    UILabel *priceL = [[UILabel alloc] initWithFrame:CGRectMake(favoriteL.frame.origin.x, favoriteL.frame.origin.y, favoriteL.frame.size.width, favoriteL.frame.size.height)];
    priceL.text = @"  按价格";
    priceL.textColor = [UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0];
    if (iPhone4s || iPhone5) {
        priceL.font = [UIFont systemFontOfSize:10.0f];
    }else{
        priceL.font = [UIFont systemFontOfSize:13.0f];
    }
    [priceV addSubview:priceL];
    //    三角形
    UIView *priceEquilateralV = [[UIView alloc]init];
    self.rangleTag = 2;
    self.priceIndicator = [self createIndicatorWithColor:[UIColor colorWithRed:176 / 255.0 green:175 / 255.0 blue:175 / 255.0 alpha:1.0] andPosition:CGPointMake(priceL.frame.origin.x + priceL.frame.size.width - 10, 17)];
    
    [priceEquilateralV.layer addSublayer:self.priceIndicator];
    [priceV addSubview:priceEquilateralV];
    
    UITapGestureRecognizer *priceEquilaterlTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(priceEquilateralTapAction)];
    [priceV addGestureRecognizer:priceEquilaterlTap];
    
    self.searchTV = [[UITableView alloc] initWithFrame:CGRectMake(0, favoriteV.frame.origin.y + favoriteV.frame.size.height + 10, WIDTH, HEIGHT - NAVIGATIONHEIGHT - self.searchTF.frame.size.height - 20) style:UITableViewStylePlain];
    self.searchTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.searchTV.showsVerticalScrollIndicator = NO;
    self.searchTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    self.searchTV.dataSource = self;
    self.searchTV.delegate = self;
    
    [self.view addSubview:_searchTV];
    
    UIGestureRecognizer *popBackGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(popBackTap:)];
    [navigationV addGestureRecognizer:popBackGR];
}

#pragma mark -- buttonAction
//按评价
-(void)commentTAction{
    //NSLog(@"嘿呀嘿呀");
    _isRurnComment = !_isRurnComment;
    
    if (self.isRurnComment) {
        
        self.stateForSearch = SearchStateForCommentsDown;
        [self animateIndicator:self.commentIndicator Forward:!_isRurnComment complete:^{
        }];
    } else {
        
        self.stateForSearch = SearchStateForCommentsUp;
        [self animateIndicator:self.commentIndicator Forward:!_isRurnComment complete:^{
        }];
    }
    [self.searchTV headerBeginRefreshing];
}

//按收藏
-(void)equilateralTapAction{
    
    _isRurnFavorite = !_isRurnFavorite;
    
    if (self.isRurnFavorite) {
        
        self.stateForSearch = SearchStateForLikenoUp;
        [self animateIndicator:self.indicator Forward:!_isRurnFavorite complete:^{
        }];
    } else {
        
        self.stateForSearch = SearchStateForLikenoDown;
        [self animateIndicator:self.indicator Forward:!_isRurnFavorite complete:^{
        }];
    }
    [self.searchTV headerBeginRefreshing];
}

//按价格
-(void)priceEquilateralTapAction{
    
    _isRurn = !_isRurn;
    
    if (self.isRurn) {
        
        self.stateForSearch = SearchStateForPriceDown; //从高到低
        [self animateIndicator:self.priceIndicator Forward:!_isRurn complete:^{
        }];
    } else {
        
        self.stateForSearch = SearchStateForPriceUp;  //从低到高
        [self animateIndicator:self.priceIndicator Forward:!_isRurn complete:^{
        }];
    }
    [self.searchTV headerBeginRefreshing];
}

//三角的动画效果
- (void)animateIndicator:(CAShapeLayer *)indicator Forward:(BOOL)forward complete:(void(^)())complete {
    //NSLog(@"大河向东流");
    [CATransaction begin];
    [CATransaction setAnimationDuration:0.25];
    [CATransaction setAnimationTimingFunction:[CAMediaTimingFunction functionWithControlPoints:0.4 :0.0 :0.2 :1.0]];
    
    CAKeyframeAnimation *anim = [CAKeyframeAnimation animationWithKeyPath:@"transform.rotation"];
    anim.values = forward ? @[@0, @0 ] : @[ @(M_PI), @(M_PI) ];
    
    if (!anim.removedOnCompletion){
        [indicator addAnimation:anim forKey:anim.keyPath];
        
    } else {
        [indicator addAnimation:anim forKey:anim.keyPath];
        [indicator setValue:anim.values.lastObject forKeyPath:anim.keyPath];
        
    }
    [CATransaction commit];
    
    complete();
}

//画三角形
- (CAShapeLayer *)createIndicatorWithColor:(UIColor *)color andPosition:(CGPoint)point {
    CAShapeLayer *layer = [CAShapeLayer new];
    
    UIBezierPath *path = [UIBezierPath new];
    if (_rangleTag == 1) {
        
        [path moveToPoint:CGPointMake(0, 0)];
        [path addLineToPoint:CGPointMake(8, 0)];
        [path addLineToPoint:CGPointMake(4, 6)];
    } else {
    
        [path moveToPoint:CGPointMake(4, 0)];
        [path addLineToPoint:CGPointMake(0, 6)];
        [path addLineToPoint:CGPointMake(8, 6)];
    }
    [path closePath];
    
    layer.path = path.CGPath;
    layer.lineWidth = 0.8;
    layer.fillColor = color.CGColor;
    
    CGPathRef bound = CGPathCreateCopyByStrokingPath(layer.path, nil, layer.lineWidth, kCGLineCapButt, kCGLineJoinMiter, layer.miterLimit);
    layer.bounds = CGPathGetBoundingBox(bound);
    CGPathRelease(bound);
    layer.position = point;
    
    return layer;
}

#pragma mark -- tapGestureAction
-(void)popBackTap:(UITapGestureRecognizer *)tap
{
    self.searchTF.text = @"搜索：学校/专业/导师";
    [self.searchTF resignFirstResponder];
}

#pragma mark -- textfieldDelegate
-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    if (textField.text.length == 0) {
        
        textField.text = @"搜索：学校/专业/导师";
    } else {
        
        self.searchKey = textField.text;
        [self.searchTV headerBeginRefreshing];
    }
    
    [textField resignFirstResponder];
    return NO;
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent]; //info中的status置为NO
    
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
    
    self.tabBarController.tabBar.hidden = YES;
}

#pragma mark -- 设置当前页的statusBar的字体颜色 iOS9之后 (info中的status置为YES)
-(UIStatusBarStyle)preferredStatusBarStyle
{
    return UIStatusBarStyleLightContent;
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_searchArray.count == 0) {
        
        return 0;
    }
    return _searchArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *searchCellIdentifier = @"searchCell";
    
    SearchTutorTableViewCell *searchCell = [tableView dequeueReusableCellWithIdentifier:searchCellIdentifier];
    
    if (searchCell == nil) {
        
        searchCell = [[SearchTutorTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:searchCellIdentifier];
        searchCell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    if (_searchArray.count == 0) {
        
        return searchCell;
    }
    
    SearchTutorModel *tmp = [_searchArray objectAtIndex:indexPath.row];
    
    [searchCell.searchIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconurl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    tmp.simpleinfo = [tmp.simpleinfo stringByReplacingOccurrencesOfString:@"<em>" withString:@""];
    tmp.simpleinfo = [tmp.simpleinfo stringByReplacingOccurrencesOfString:@"</em>" withString:@""];
    searchCell.tagForSearchL.text = tmp.simpleinfo;
    
    CGRect searchRect1 = [searchCell.tagForSearchL.text boundingRectWithSize:CGSizeMake(220, MAXFLOAT) options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:searchCell.tagForSearchL.font} context:nil];
    searchRect1.size.height = searchCell.tagForSearchL.frame.size.height;
//    CGSize searchSize1 = [searchCell.schoolForSearchL.text sizeWithFont:[UIFont systemFontOfSize:15.0f] constrainedToSize:CGSizeMake(200, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
//    searchSize1.height = searchCell.schoolForSearchL.frame.size.height;
//    searchCell.tagForSearchL.frame = CGRectMake(searchCell.tagForSearchL.frame.origin.x, searchCell.tagForSearchL.frame.origin.y, searchRect1.size.width, searchRect1.size.height);
    
    searchCell.searchNameL.text = tmp.name;
    tmp.servicetitle = [tmp.servicetitle stringByReplacingOccurrencesOfString:@"<em>" withString:@""];
    tmp.servicetitle = [tmp.servicetitle stringByReplacingOccurrencesOfString:@"</em>" withString:@""];
    searchCell.topicForSearchL.text = tmp.servicetitle;
    searchCell.hasSeenForSearchL.text = [NSString stringWithFormat:@"%@人想见", tmp.likeno];
    searchCell.clickNumberForSearchL.text = [NSString stringWithFormat:@"%@元/小时", tmp.serviceprice];
    
    return searchCell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_searchArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    
    SearchTutorModel *tmp = [_searchArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.userID;
    
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark -- 源字符串内容是否包含或等于要搜索的字符串内容
-(void)getSearchData
{
    NSString *stateStr;
    switch (self.stateForSearch) {
        case SearchStateForLikenoUp:
            stateStr = @"likeno+";
            break;
        case SearchStateForLikenoDown:
            stateStr = @"likeno-";
            break;
        case SearchStateForPriceUp:
            stateStr = @"price+";
            break;
        case SearchStateForPriceDown:
            stateStr = @"price-";
            break;
        default:
            stateStr = @"";
            break;
    }
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForSearchTutor:self.searchKey andFilter:stateStr andPage:[NSString stringWithFormat:@"%ld", self.indexPage]] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSString *dataAgain = [dic objectForKey:@"result"];
        NSString *state = [dic objectForKey:@"state"];
        
        if ([state isEqualToString:@"success"]) {
            
            NSMutableArray *jsonArray = (NSMutableArray *)[dataAgain objectFromJSONString];
            for (NSDictionary *tmpDic in jsonArray) {
                
                SearchTutorModel *searchM = [[SearchTutorModel alloc] init];
                
                searchM.companyname = [tmpDic objectForKey:@"companyname"];
                searchM.degree = [tmpDic objectForKey:@"degree"];
                searchM.iconurl = [tmpDic objectForKey:@"iconurl"];
                searchM.userID = [tmpDic objectForKey:@"id"];
                searchM.index_name = [tmpDic objectForKey:@"index_name"];
                searchM.level = [tmpDic objectForKey:@"level"];
                searchM.likeno = [tmpDic objectForKey:@"likeno"];
                searchM.major = [tmpDic objectForKey:@"major"];
                searchM.name = [tmpDic objectForKey:@"name"];
                searchM.position = [tmpDic objectForKey:@"position"];
                searchM.schoolname = [tmpDic objectForKey:@"schoolname"];
                searchM.servicecontent = [tmpDic objectForKey:@"servicecontent"];
                searchM.serviceprice = [tmpDic objectForKey:@"serviceprice"];
                searchM.servicetime = [tmpDic objectForKey:@"servicetime"];
                searchM.servicetitle = [tmpDic objectForKey:@"servicetitle"];
                searchM.simpleinfo = [tmpDic objectForKey:@"simpleinfo"];
                searchM.timeperweek = [tmpDic objectForKey:@"timeperweek"];
                searchM.tipcontent = [tmpDic objectForKey:@"tipcontent"];
                
                searchM.name = [searchM.name stringByReplacingOccurrencesOfString:@"<em>" withString:@""];
                searchM.name = [searchM.name stringByReplacingOccurrencesOfString:@"</em>" withString:@""];
                
                [self.searchArray addObject:searchM];
            }
        }
        
        if (self.searchArray.count == 0) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"没有此人" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [self.view addSubview:alert];
            
            alert.delegate = self;
            
            [alert show];
        }
        
        [self.searchTV reloadData];
    }];
}

#pragma mark -- 下拉刷新
-(void)headerRefesh {
    
    self.downUpdata = YES;
    self.indexPage = 1;
    //让菊花旋转起来
    //        self.MBHUD = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    //        self.MBHUD.labelText = @"正在加载中...";
    //        [self.MBHUD show:YES];
    
    [_searchArray removeAllObjects];
    [self getSearchData];
    
    // 2.2秒后刷新表格UI
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        // 刷新表格
        [self.searchTV reloadData];
        
        // 拿到当前的下拉刷新控件，结束刷新状态
        [self.searchTV headerEndRefreshing];
//    });
}

#pragma mark -- 上拉加载
-(void)footerRefesh {
    
    self.downUpdata = NO;
    self.indexPage ++;
    //        self.MBHUD = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    //        self.MBHUD.labelText = @"正在加载中...";
    //        [self.MBHUD show:YES];
    
    [self getSearchData];
    
    // 2.2秒后刷新表格UI
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        // 刷新表格
        [self.searchTV reloadData];
        [self.searchTV footerEndRefreshing];
//    });
}

#pragma mark -- 自动弹回警示框
-(void)showAlert:(NSString *)message
{
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:0.8f target:self selector:@selector(timerFireMethod:) userInfo:promptAlert repeats:YES];
    
    [promptAlert show];
}

-(void)timerFireMethod:(NSTimer *)theTimer
{
    UIAlertView *promptAlert = (UIAlertView *)[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

#pragma mark -- scrollviewDelegate
-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self.searchTF resignFirstResponder];
    
    if (self.searchTF.text.length == 0) {
        
        self.searchTF.text = @"搜索：学校/专业/导师";
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
