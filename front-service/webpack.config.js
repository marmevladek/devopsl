import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default {
  entry: {
    socket: './public/service/index/socket.js', 
    createButtonEvent: './public/service/create/createButtonEvent.js', 
    readBuilder: './public/service/read/readBuilder.js', 
    updateButtonEvent: './public/service/update/updateButtonEvent.js', 
  },
  output: {
    filename: '[name].bundle.js', 
    path: path.resolve(__dirname, './public/dist'), 
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.js$/, 
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader', 
        },
      },
      {
        test: /\.ejs$/, 
        use: [
          {
            loader: 'ejs-loader',
          },
        ],
      },
    ],
  },
  resolve: {
    extensions: ['.js', '.ejs'], 
  },
  mode: 'development',
};