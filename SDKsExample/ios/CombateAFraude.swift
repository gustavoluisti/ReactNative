//
//  CombateAFraude.swift
//  SDKsExample
//
//  Created by Frederico Hansel dos Santos Gassen on 08/10/20.
//

import UIKit
import Foundation
import DocumentDetector
import PassiveFaceLiveness

@objc(CombateAFraude)
class CombateAFraude: NSObject, PassiveFaceLivenessControllerDelegate {
  
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc(passiveFaceLiveness:)
    func passiveFaceLiveness(mobileToken: String) {
        let passiveFaceLiveness = PassiveFaceLiveness.Builder(mobileToken: mobileToken)
            .build()
        
        DispatchQueue.main.async {
            let controller = UIApplication.shared.keyWindow!.rootViewController
          
            let passiveVC = PassiveFaceLivenessController(passiveFaceLiveness: passiveFaceLiveness)
            passiveVC.passiveFaceLivenessDelegate = self
          
            controller?.present(passiveVC, animated: true, completion: nil)
        }
    }

    func passiveFaceLivenessController(_ passiveFacelivenessController: PassiveFaceLivenessController, didFinishWithResults results: PassiveFaceLivenessResult) {
        
    }
    
    func passiveFaceLivenessControllerDidCancel(_ passiveFacelivenessController: PassiveFaceLivenessController) {
        
    }
    
    func passiveFaceLivenessController(_ passiveFacelivenessController: PassiveFaceLivenessController, didFailWithError error: PassiveFaceLivenessFailure) {
       
    }

  
}
