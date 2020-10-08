import React, { useEffect, useState } from 'react';
import { Button, NativeEventEmitter, NativeModules, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Colors } from 'react-native/Libraries/NewAppScreen';

const App: () => React$Node = () => {

  const [passiveFaceLivenessResult, setPassiveFaceLivenessResult] = useState(null);
  const [documentDetectorResult, setDocumentDetectorResult] = useState(null);
  // const deviceInfoEmitter = new NativeEventEmitter();


  // useEffect(() => {
  //   async function passiveFaceLivenessSuccess(params) {
  //     setPassiveFaceLivenessResult("Selfie: "+params.selfiePath);
  //   }

  //   async function passiveFaceLivenessError(params) {
  //     setPassiveFaceLivenessResult("Erro: "+params.error);
  //   }

  //   async function documentDetectorSuccess(params) {
  //     setDocumentDetectorResult("Frente do documento: "+params.frontPath+"\n\nVerso do documento: "+params.backPath);
  //   }

  //   async function documentDetectorError(params) {
  //     setDocumentDetectorResult("Erro: "+params.error);
  //   }

  //   deviceInfoEmitter.addListener('CAF_PassiveFaceLiveness_Success', passiveFaceLivenessSuccess);
  //   deviceInfoEmitter.addListener('CAF_PassiveFaceLiveness_Error', passiveFaceLivenessError);
  //   deviceInfoEmitter.addListener('CAF_DocumentDetector_Success', documentDetectorSuccess);
  //   deviceInfoEmitter.addListener('CAF_DocumentDetector_Error', documentDetectorError);

  //   return () => {
  //     deviceInfoEmitter.removeListener('CAF_PassiveFaceLiveness_Success', passiveFaceLivenessSuccess);
  //     deviceInfoEmitter.removeListener('CAF_PassiveFaceLiveness_Error', passiveFaceLivenessError);
  //     deviceInfoEmitter.removeListener('CAF_DocumentDetector_Success', documentDetectorSuccess);
  //     deviceInfoEmitter.removeListener('CAF_DocumentDetector_Error', documentDetectorError);
  //   };
  // }, [deviceInfoEmitter]);

  return (
    <>
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Button style={styles.button}
                      title='PassiveFaceLiveness'
                      onPress={() => {
                        NativeModules.CombateAFraude.passiveFaceLiveness("")
                      }}/>
            </View>

            <View style={styles.sectionContainer}>
              <Text>{'PassiveFaceLivenessResult: ' + passiveFaceLivenessResult}</Text>
            </View>
            <View style={styles.sectionContainer}>
              <Button style={styles.button}
                      title='DocumentDetector - CNH'
                      onPress={() => {
                        // NativeModules.CAF.documentDetectorCnh();
                      }}/>
            </View>
            <View style={styles.sectionContainer}>
              <Button style={styles.button}
                      title='DocumentDetector - RG'
                      onPress={() => {
                        // NativeModules.CAF.documentDetectorRg();
                      }}/>
            </View>

            <View style={styles.sectionContainer}>
              <Text>{'DocumentDetectorResult: ' + documentDetectorResult}</Text>
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
});

export default App;