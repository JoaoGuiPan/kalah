import { House } from './house.model';

export interface Match {
  id: number;
  startingNumberOfSeedsPerHouse: number;
  board: House[];
  southPlayer: string;
  northPlayer: string;
  currentTurnPlayer: string;
  lastTurnTimestamp: Date;
  active: boolean;
  winner: string;
  southPlayerScore: number;
  northPlayerScore: number;
}
