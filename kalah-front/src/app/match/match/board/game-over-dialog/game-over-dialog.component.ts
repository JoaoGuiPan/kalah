import { Component, OnInit, Inject } from '@angular/core';
import { Match } from 'src/app/model/match.model';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-game-over-dialog',
  templateUrl: './game-over-dialog.component.html',
  styleUrls: ['./game-over-dialog.component.scss']
})
export class GameOverDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Match, private dialogRef: MatDialogRef<GameOverDialogComponent>) {
  }

  static showDialog(dialog: MatDialog, match: Match) {
    const dialogRef = dialog.open(GameOverDialogComponent, { disableClose: true, autoFocus: true, data: match, width: '50%' });
    return dialogRef.afterClosed().toPromise();
  }

  ngOnInit(): void {
  }

  async close(result: boolean) {
    this.dialogRef.close(result);
  }

}
