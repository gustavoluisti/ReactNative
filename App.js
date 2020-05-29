import React, { useEffect, useState }                                                                  from 'react';
import { Button, NativeEventEmitter, NativeModules, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';

import { Colors } from 'react-native/Libraries/NewAppScreen';

const App: () => React$Node = () => {

  const [photoInfo, setPhotoInfo] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [isLoading, setIsLoading] = useState(null);
  const deviceInfoEmitter = new NativeEventEmitter();


  useEffect(() => {
    async function passiveFaceLivenessListenner(params) {
      const {selfieBase64, missedAttemps} = params;
      setPhotoInfo({selfieBase64, missedAttemps});
    }

    async function combateAFraudeErrorListenner(params) {
      setErrorMessage(params.message);
      setIsLoading(false);
    }

    deviceInfoEmitter.addListener('combateAFraude_passiveFaceLiveness', passiveFaceLivenessListenner);
    deviceInfoEmitter.addListener('combateAFraude_error', combateAFraudeErrorListenner);

    return () => {
      deviceInfoEmitter.removeListener('combateAFraude_passiveFaceLiveness', passiveFaceLivenessListenner);
      deviceInfoEmitter.removeListener('combateAFraude_error', combateAFraudeErrorListenner);
    };
  }, [deviceInfoEmitter]);

  return (
    <>
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Button style={styles.button}
                      title='Open Faces Liveness'
                      onPress={() => NativeModules.CombateAFraude.passiveFaceLiveness()}/>
            </View>

            <View style={styles.sectionContainer}>
              <Text>{photoInfo ? 'Selfie is saved in phone storage' : 'Not saved yet'}</Text>
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
