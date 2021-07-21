//
//  LivenessCustomViewController.swift
//  iOS-example
//
//  Created by Daniel Seitenfus on 25/06/21.
//

import UIKit
import PassiveFaceLiveness

class LivenessViewController: UIViewController, PassiveFaceLivenessCustomViewDelegate {
  
  @IBOutlet weak var previewView: UIView!
  @IBOutlet weak var faceMask: UIImageView!
  @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
  @IBOutlet weak var labelMessage: UILabel!
  
  var controller: PassiveFaceLivenessCustomViewController?
  
  public var module: CombateAFraude!
  
  public var passiveFaceLiveness: PassiveFaceLiveness? = nil
  
  // MARK: - Lifecycle
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    if let passiveFaceLiveness = self.passiveFaceLiveness {
      controller = PassiveFaceLivenessCustomViewController(passiveFaceLiveness: passiveFaceLiveness, viewController: self, previewView: previewView)
    }
  }
  
  override func viewWillDisappear(_ animated: Bool) {
    super.viewWillDisappear(animated)
    
    self.controller?.viewWillDisappear()
  }
  
  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)
    
    self.controller?.viewDidAppear()
  }
  
  override func viewDidLayoutSubviews() {
    super.viewDidLayoutSubviews()
    self.controller?.viewDidLayoutSubviews()
  }
  
  // MARK: - UI Changes
  
  func show(loading show: Bool) {
    if(show){
      activityIndicator.startAnimating()
    }else{
      activityIndicator.stopAnimating()
    }
  }
  
  func show(message text: String) {
    labelMessage.text = text
  }
  
  func show(stepName text: String) {
    
  }
  
  func show(mask type: MaskType) {
    
    switch type {
    case .normal:
      self.faceMask.image = UIImage(named: "liveness_mask_white")
      break
    case .success:
      self.faceMask.image = UIImage(named: "liveness_mask_green")
      break
    case .error:
      self.faceMask.image = UIImage(named: "liveness_mask_red")
      break
    default:
      self.faceMask.image = UIImage(named: "liveness_mask_white")
    }
  }
  
  // MARK: - SDK Response
  
  func passiveFaceLivenessController(didFinishWithResults results: PassiveFaceLivenessResult) {
    self.module.sendEventWithResultsPassiveFaceLiveness(results)
    self.openCompleted() //Sucesso
  }
  
  func passiveFaceLivenessControllerDidCancel() {
    self.module.sendEventCancelPassiveFaceLiveness()
    self.openCompleted() //Cancelado
  }
  
  func passiveFaceLivenessController(didFailWithError error: PassiveFaceLivenessFailure) {
    self.module.sendEventWithErrorPassiveFaceLiveness(error)
    self.openCompleted() //Erro
  }
  
  // MARK: - Screen Actions
  
  func openCompleted(){
    self.dismiss(animated: true, completion: nil)
  }
  
  @IBAction func closeButtonClick(_ sender: Any) {
    self.controller?.cancelButtonClick()
  }
  
}
