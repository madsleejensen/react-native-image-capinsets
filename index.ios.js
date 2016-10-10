import React, { Component } from 'react';
import {
  StyleSheet,
  Image,
} from 'react-native';

const styles = StyleSheet.create({

});

class ImageCapInset extends Component {
  render() {
    return (
      <Image
        {...this.props}
        resizeMode={Image.resizeMode.stretch}
      />
    );
  }
}

export default ImageCapInset;
