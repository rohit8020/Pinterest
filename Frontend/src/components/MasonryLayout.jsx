import React from 'react';
import Masonry from 'react-masonry-css';
import Pin from './Pin';
import Board from './Board';

const breakpointColumnsObj = {
  default: 4,
  3000: 6,
  2000: 5,
  1200: 3,
  1000: 2,
  500: 1,
};

const MasonryLayout = ({pins,boards}) => (
  <Masonry className="flex animate-slide-fwd" breakpointCols={breakpointColumnsObj}>
    {/* {pins?.map((pin) => <Pin key={pin.pinId} pin={pin} className="w-max" />)} */}
    {pins &&
      pins.map((pin) => (
        <Pin key={pin.pinId} pin={pin} className="w-full md:w-1/2 lg:w-1/3" />
      ))}
    {boards &&
      boards.map((board) => (
        <Board key={board.boardId} board={board} className="w-full md:w-1/2 lg:w-1/3" />
      ))}
  </Masonry>
);

export default MasonryLayout;
