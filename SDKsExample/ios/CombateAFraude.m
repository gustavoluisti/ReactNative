//
//  CombateAFraude.m
//  SDKsExample
//
//  Created by Frederico Hansel dos Santos Gassen on 08/10/20.
//

#import "React/RCTBridgeModule.h"
@interface RCT_EXTERN_MODULE(CombateAFraude, NSObject)

RCT_EXTERN_METHOD(passiveFaceLiveness:(NSString *)mobileToken)

@end
