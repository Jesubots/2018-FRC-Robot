package org.usfirst.frc.team5809.robot;

public class Destination {

	public static enum eFieldSide {
		kLeft, kRight, kMiddle, kUnknown
	};

	public static enum eFieldDistance {
		kNearSide, kNear, kMiddle, kFar, kUnknown, 
	};	
	
	public static enum NearSideDestination  {
		DRIVE_SEG1 {
			@Override
			public double getDriveData() {
				return RobotMap.EncoderDistanceMap.kNearSideSeg1;
			}
		},
		DRIVE_TURN1 {
			@Override
			public double getDriveData() {
				if (OI.getDestination().getFieldSide() ==  Destination.eFieldSide.kLeft)
					return 90.0;  //turn left
				else
					return -90.0;  // turn right
			}
		},
		DRIVE_SEG2 {
			@Override
			public double getDriveData() {
				return RobotMap.EncoderDistanceMap.kNearSideSeg2;
			}
		},
		DRIVE_TURN2 {
			@Override
			public double getDriveData() {
				if (OI.getDestination().getFieldSide() ==  Destination.eFieldSide.kLeft)
					return -90.0;  // turn right
				else
					return 90.0;   // turn left
			}
		},
		DRIVE_SEG3 {
			@Override
			public double getDriveData() {
				return RobotMap.EncoderDistanceMap.kNearSideSeg3;
			}
		};

		public abstract double getDriveData();
	}

	private eFieldSide fieldSide;
	private eFieldDistance fieldDistance;

	public Destination() {
		fieldSide = eFieldSide.kUnknown;
		fieldDistance = eFieldDistance.kUnknown;
	}

	public Destination(char position, eFieldDistance distance) {
		if (position == 'L') {
			this.fieldSide = eFieldSide.kLeft;
		} else if (position == 'R') {
			this.fieldSide = eFieldSide.kRight;
		} else {
			this.fieldSide = eFieldSide.kUnknown;
		}

		this.fieldDistance = distance;
	}

	public Destination(eFieldSide side, eFieldDistance distance) {
		this.fieldSide = side;
		this.fieldDistance = distance;
	}

	public eFieldSide getFieldSide() {
		return fieldSide;
	}

	public void setFieldSide(eFieldSide fieldSide) {
		this.fieldSide = fieldSide;
	}

	public eFieldDistance getFieldDistance() {
		return fieldDistance;
	}

	public void setFieldDistance(eFieldDistance fieldDistance) {
		this.fieldDistance = fieldDistance;
	}

	public double getPivotTurnDegrees() {
		switch (this.fieldSide) {
		case kLeft: // desination is on Left, so turn Right
			return -90.0;
		case kRight: // destination is on right, so turn Left
			return 90.0;
		default:
			return 0.0;
		}
	}

	public double getEncoderDistance() {
		switch (this.fieldDistance) {
		case kNear:
			return RobotMap.EncoderDistanceMap.kNearDistance;
		case kMiddle:
			return RobotMap.EncoderDistanceMap.kMiddleDistance;
		case kFar:
			return RobotMap.EncoderDistanceMap.kFarDistance;
		case kNearSide:
			return RobotMap.EncoderDistanceMap.kNearSide;
		default:
			return RobotMap.EncoderDistanceMap.kUnknownDistance;
		}
	}

	public String toString() {
		String sRet;

		switch (this.fieldSide) {
		case kLeft: // desination is on Left, so turn Right
			sRet = "kLeft, ";
			break;
		case kRight: // destination is on right, so turn Left
			sRet = "kRight, ";
			break;
		case kMiddle: // destination is on right, so turn Left
			sRet = "kMiddle, ";
			break;
		default:
			sRet = "kUnknown, ";
			break;
		}

		switch (this.fieldDistance) {
		case kNearSide:
			sRet = sRet + "kNearSide";
			break;
		case kNear:
			sRet = sRet + "kNear";
			break;
		case kMiddle:
			sRet = sRet + "kMiddle";
			break;
		case kFar:
			sRet = sRet + "kFar";
			break;
		default:
			sRet = sRet + "kUnknown";
		}

		return sRet;
	}
}
